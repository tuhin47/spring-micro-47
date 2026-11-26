package me.tuhin47.awsreactive.s3.service;

import me.tuhin47.awsreactive.common.AmazonProperties;
import me.tuhin47.awsreactive.s3.domain.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.Upload;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AwsS3ServiceTest {

    @Mock
    private S3Client s3Client;
    @Mock
    private S3TransferManager transferManager;
    @Mock
    private SocketService socketService;

    private AwsS3Service awsS3Service;

    @BeforeEach
    void setUp() {
        AmazonProperties amazonProperties = new AmazonProperties();
        amazonProperties.setUrl("http://localhost:4566");
        amazonProperties.getS3().setBucketName("files");
        awsS3Service = new AwsS3Service(s3Client, transferManager, amazonProperties, socketService);
    }

    @Test
    void uploadsLargeFileAndReportsSuccess() {
        Upload upload = org.mockito.Mockito.mock(Upload.class);
        when(transferManager.upload(any(UploadRequest.class))).thenReturn(upload);
        when(upload.completionFuture()).thenReturn(CompletableFuture.completedFuture(null));

        FileInfo fileInfo = awsS3Service.uploadLargeFileWithProgress("document.txt", new byte[]{1, 2, 3}, "session-1");

        assertTrue(fileInfo.isUploadSuccessFull());
        assertEquals("http://localhost:4566/files/document.txt", fileInfo.getFileUrl());
        verify(transferManager).upload(any(UploadRequest.class));
        verify(socketService, never()).sendMessageTo(any(), any());
    }

    @Test
    void reportsFailedLargeFileUpload() {
        Upload upload = org.mockito.Mockito.mock(Upload.class);
        when(transferManager.upload(any(UploadRequest.class))).thenReturn(upload);
        when(upload.completionFuture()).thenReturn(CompletableFuture.failedFuture(new IOException("upload failed")));

        FileInfo fileInfo = awsS3Service.uploadLargeFileWithProgress("document.txt", new byte[]{1, 2, 3}, "session-1");

        assertFalse(fileInfo.isUploadSuccessFull());
    }

    @Test
    void uploadsAnObjectAndListsObjectsFromConfiguredBucket() {
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
            .thenReturn(PutObjectResponse.builder().build());
        S3Object object = S3Object.builder().key("document.txt").build();
        when(s3Client.listObjectsV2(any(ListObjectsV2Request.class)))
            .thenReturn(ListObjectsV2Response.builder().contents(object).build());

        FileInfo fileInfo = awsS3Service.uploadObjectToS3("document.txt", new byte[]{1, 2, 3});

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));
        assertEquals("files", requestCaptor.getValue().bucket());
        assertEquals("document.txt", requestCaptor.getValue().key());
        assertTrue(fileInfo.isUploadSuccessFull());
        assertSame(object, awsS3Service.listObjects().getFirst());
    }
}
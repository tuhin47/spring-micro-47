package me.tuhin47.awsreactive.s3.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.awsreactive.common.AmazonProperties;
import me.tuhin47.awsreactive.s3.domain.FileInfo;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.Upload;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;
import software.amazon.awssdk.transfer.s3.progress.TransferListener;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;
    private final S3TransferManager transferManager;
    private final AmazonProperties amazonProperties;
    private final SocketService socketService;

    public FileInfo uploadLargeFileWithProgress(String fileName, byte[] fileData, final String sessionId) {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Uploading large file '{}' to bucket: '{}' with progress tracking", fileName, bucketName);
        int contentLength = fileData.length;
        TransferListener progressListener = getProgressListener(sessionId, contentLength);
        UploadRequest uploadRequest = UploadRequest.builder()
                                                   .putObjectRequest(request -> request.bucket(bucketName).key(fileName))
                                                   .requestBody(AsyncRequestBody.fromBytes(fileData))
                                                   .addTransferListener(progressListener)
                                                   .build();
        Upload upload = transferManager.upload(uploadRequest);
        boolean isUploadSuccessFull = true;
        try {
            upload.completionFuture().get();
            log.info("Upload complete.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Upload was interrupted.", e);
            isUploadSuccessFull = false;
        } catch (ExecutionException e) {
            log.error("Upload failed.", e.getCause());
            isUploadSuccessFull = false;
        }
        return createFileInfo(fileName, isUploadSuccessFull);
    }

    public FileInfo createFileInfo(String fileName, boolean isUploadSuccessFull) {
        String bucketName = amazonProperties.getS3().getBucketName();
        String fileUrl = amazonProperties.getUrl() + "/" + bucketName + "/" + fileName;
        return new FileInfo(fileName, fileUrl, isUploadSuccessFull);
    }

    private TransferListener getProgressListener(String sessionId, final long contentLength) {
        return new TransferListener() {
            @Override
            public void bytesTransferred(Context.BytesTransferred context) {
                long bytesUploaded = context.progressSnapshot().transferredBytes();
                if (contentLength > 0) {
                    double percentUploaded = (double) bytesUploaded * 100 / contentLength;
                    socketService.sendMessageTo(sessionId, Double.toString(percentUploaded));
                    log.info(String.format("Upload progress: %.2f%%", percentUploaded));
                }
            }
        };
    }


    public FileInfo uploadObjectToS3(String fileName, byte[] fileData) {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Uploading file '{}' to bucket: '{}' ", fileName, bucketName);
        var putObjectResponse = s3Client.putObject(
            PutObjectRequest.builder().bucket(bucketName).key(fileName).contentLength((long) fileData.length).build(),
            RequestBody.fromBytes(fileData));
        log.info("Uploading completed {} ", putObjectResponse);
        return createFileInfo(fileName, Objects.nonNull(putObjectResponse));
    }

    public ResponseInputStream<?> downloadFileFromS3Bucket(final String fileName) {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Downloading file '{}' from bucket: '{}' ", fileName, bucketName);
        return s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(fileName).build());
    }

    public List<S3Object> listObjects() {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Retrieving object summaries for bucket '{}'", bucketName);
        return s3Client.listObjectsV2(ListObjectsV2Request.builder().bucket(bucketName).build()).contents();
    }
}

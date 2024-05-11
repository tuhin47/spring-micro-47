package me.tuhin47.awssamples.s3.service;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.awssamples.common.AmazonProperties;
import me.tuhin47.awssamples.s3.domain.FileInfo;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;
    private final TransferManager transferManager;
    private final AmazonProperties amazonProperties;
    private final SocketService socketService;

    public FileInfo uploadLargeFileWithProgress(String fileName, byte[] fileData, final String sessionId) {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Uploading large file '{}' to bucket: '{}' with progress tracking", fileName, bucketName);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileData.length);

        try {
            ProgressListener progressListener = new ProgressListener() {
                private long bytesUploaded = 0; // Track the amount of data uploaded
                private final long totalBytes = objectMetadata.getContentLength(); // Total size of the file

                @Override
                public void progressChanged(ProgressEvent progressEvent) {
                    bytesUploaded += progressEvent.getBytesTransferred(); // Update the uploaded byte count
                    if (totalBytes > 0) {
                        Long percentUploaded = (bytesUploaded * 100) / totalBytes;
                        socketService.sendMessageTo(sessionId, percentUploaded.toString());
                        log.info(String.format("Upload progress: %d%%", percentUploaded));
                    }
                }
            };
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, byteArrayInputStream, objectMetadata).withGeneralProgressListener(progressListener);
            Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForCompletion();
            log.info("Upload complete.");
            String fileUrl = amazonProperties.getUrl() + "/" + bucketName + "/" + fileName;
            return new FileInfo(fileName, fileUrl, true);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Upload was interrupted.", e);
        }
        return null;
    }


    public FileInfo uploadObjectToS3(String fileName, byte[] fileData) {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Uploading file '{}' to bucket: '{}' ", fileName, bucketName);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileData.length);
        String fileUrl = amazonProperties.getUrl() + "/" + bucketName + "/" + fileName;
        PutObjectResult putObjectResult = amazonS3.putObject(bucketName, fileName, byteArrayInputStream, objectMetadata);
        return new FileInfo(fileName, fileUrl, Objects.nonNull(putObjectResult));
    }

    public S3ObjectInputStream downloadFileFromS3Bucket(final String fileName) {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Downloading file '{}' from bucket: '{}' ", fileName, bucketName);
        final S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        return s3Object.getObjectContent();
    }

    public List<S3ObjectSummary> listObjects() {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Retrieving object summaries for bucket '{}'", bucketName);
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }
}

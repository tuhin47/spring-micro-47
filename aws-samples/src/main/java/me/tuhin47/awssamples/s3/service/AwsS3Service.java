package me.tuhin47.awssamples.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
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
    private final AmazonProperties amazonProperties;

    public FileInfo uploadObjectToS3(String fileName, byte[] fileData) {
        String bucketName = amazonProperties.getS3().getBucketName();
        log.info("Uploading file '{}' to bucket: '{}' ", fileName, bucketName);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileData.length);
        String fileUrl = amazonProperties.getUrl() + "/" + bucketName + "/" + fileName;
        PutObjectResult putObjectResult =
            amazonS3.putObject(
                bucketName, fileName, byteArrayInputStream, objectMetadata);
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

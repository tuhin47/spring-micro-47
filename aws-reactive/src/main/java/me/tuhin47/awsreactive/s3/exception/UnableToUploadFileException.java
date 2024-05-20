package me.tuhin47.awsreactive.s3.exception;

public class UnableToUploadFileException extends RuntimeException {

    public UnableToUploadFileException(String fileName) {
        super("Unable to upload file with name: " + fileName);
    }
}

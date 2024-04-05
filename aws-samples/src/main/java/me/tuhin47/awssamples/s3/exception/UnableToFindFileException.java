package me.tuhin47.awssamples.s3.exception;

public class UnableToFindFileException extends RuntimeException {

    public UnableToFindFileException(String fileName) {
        super("Unable to find file with name: " + fileName);
    }
}
package me.tuhin47.config.exception.apierror;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {

    private String errorCode;
    private int status;
    private boolean logged = false;

    public CustomException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomException{" +
            "errorCode='" + errorCode + '\'' +
            ", status=" + status +
            ", message=" + getMessage() +
            '}';
    }
}

package me.tuhin47.config.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class JwtTokenMissingException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public JwtTokenMissingException(String msg) {
        super(msg);
    }

}

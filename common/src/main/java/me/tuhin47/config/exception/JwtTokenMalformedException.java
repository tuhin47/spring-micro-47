package me.tuhin47.config.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class JwtTokenMalformedException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public JwtTokenMalformedException(String msg) {
        super(msg);
    }

}

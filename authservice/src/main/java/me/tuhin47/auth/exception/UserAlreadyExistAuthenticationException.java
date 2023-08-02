package me.tuhin47.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author Chinna
 *
 */
public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5570981880007077317L;

	public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }

}

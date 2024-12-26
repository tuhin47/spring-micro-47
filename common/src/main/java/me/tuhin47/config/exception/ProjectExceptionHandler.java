package me.tuhin47.config.exception;

import me.tuhin47.config.exception.apierror.CustomException;
import me.tuhin47.config.exception.apierror.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public interface ProjectExceptionHandler {
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex);

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex);

    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex);

    @ExceptionHandler(org.springframework.orm.jpa.JpaObjectRetrievalFailureException.class)
    ResponseEntity<Object> handleJpaObjectRetrievalFailureException(org.springframework.orm.jpa.JpaObjectRetrievalFailureException ex);

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request);

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request);

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request);

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request);

    @ExceptionHandler(CustomException.class)
    ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request);

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleException(Exception e);
}

package me.tuhin47.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {

    private final ProjectExceptionHandler projectExceptionHandler;
    private final ObjectMapper objectMapper;

    public void handleAuthenticationException(HttpServletResponse response, AuthenticationException e) throws IOException {
        ResponseEntity<Object> objectResponseEntity = projectExceptionHandler.handleAuthenticationException(e, null);
        populateResponse(response, objectResponseEntity);
    }


    public void handleAccessDeniedException(HttpServletResponse response, AccessDeniedException e) throws IOException {
        ResponseEntity<Object> objectResponseEntity = projectExceptionHandler.handleAccessDeniedException(e, null);
        populateResponse(response, objectResponseEntity);
    }

    private void populateResponse(HttpServletResponse response, ResponseEntity<Object> objectResponseEntity) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(objectResponseEntity.getStatusCodeValue());
        response.setContentType("application/json");
        response.getOutputStream().write(objectMapper.writeValueAsBytes(objectResponseEntity.getBody()));
    }
}

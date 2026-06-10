package com.cabeleleira.leila.salao.shared.exceptions.handler;

import com.cabeleleira.leila.salao.shared.exceptions.AccessDeniedException;
import com.cabeleleira.leila.salao.shared.exceptions.BusinessException;
import com.cabeleleira.leila.salao.shared.exceptions.EntityAlreadyExistsException;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public final class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(
                Instant.now(),
                status.value(),
                "Validation error",
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(NotFoundObjectException.class)
    public ResponseEntity<StandardError> handleNotFoundException(
            NotFoundObjectException ex,
            HttpServletRequest request
    ) {
        String message = "Not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(
                Instant.now(),
                status.value(),
                message,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<StandardError> handleEntityAlreadyExistsException(
            EntityAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        String message = "Conflicting Entity";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError standardError = new StandardError(
                Instant.now(),
                status.value(),
                message,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> handlerIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        String message = "Argument is wrong!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                message,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> handlerAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        String message = "Access denied!";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                message,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> handlerBusinessException(
            BusinessException e,
            HttpServletRequest request
    ) {
        String message = "Business Exeception";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                message,
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }
}

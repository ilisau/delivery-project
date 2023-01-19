package com.solvd.dp.web.controller;

import com.solvd.dp.domain.exception.ExceptionBody;
import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setDetails(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolationException(ConstraintViolationException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        exceptionBody.setDetails(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ResourceMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceMappingException(ResourceMappingException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalArgumentException(IllegalArgumentException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalStateException(IllegalStateException e) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleAuthenticationException() {
        return new ExceptionBody("Incorrect credentials");
    }

    @ExceptionHandler({AccessDeniedException.class, com.solvd.dp.domain.exception.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDeniedException() {
        return new ExceptionBody("Access denied");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e) {
        return new ExceptionBody(e.getMessage());
    }

}

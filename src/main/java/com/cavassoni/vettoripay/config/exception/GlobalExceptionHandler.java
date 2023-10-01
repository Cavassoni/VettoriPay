package com.cavassoni.vettoripay.config.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final var errors = new ArrayList<String>();
        for (FieldError error : exception.getBindingResult().getFieldErrors())
            errors.add(error.getField() + ": " + error.getDefaultMessage());

        for (ObjectError error : exception.getBindingResult().getGlobalErrors())
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message("Validation failed for " + exception.getObjectName())
                        .errorDetail(errors)
                        .build());
    }

    @ExceptionHandler(FindByIdNotFound.class)
    public ResponseEntity<Object> handleFindByIdNotFound(FindByIdNotFound exception, WebRequest request) {
        logger.error("FindByIdNotFound: {}", exception.getMessage());

        return ResponseEntity //
                .status(HttpStatus.NOT_FOUND) //
                .body(ErrorResponse.builder() //
                        .message(exception.getMessage()) //
                        .rootCause(ExceptionUtils.getRootCauseMessage(exception)) //
                        .build());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException exception, WebRequest request) {
        logger.error("ValidationException: {}", exception.getMessage());
        return ResponseEntity //
                .status(HttpStatus.BAD_REQUEST) //
                .body(ErrorResponse.builder() //
                        .message(exception.getMessage()) //
                        .build());
    }

    @ExceptionHandler(BadCredentials.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentials exception, WebRequest request) {
        logger.error("BadCredentials: {}", exception.getMessage());
        return ResponseEntity //
                .status(HttpStatus.UNAUTHORIZED) //
                .body(ErrorResponse.builder() //
                        .message(exception.getMessage()) //
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {
        logger.error("Exception: " + exception.getMessage(), exception);
        return ResponseEntity //
                .status(HttpStatus.INTERNAL_SERVER_ERROR) //
                .body(ErrorResponse.builder() //
                        .message(exception.getMessage()) //
                        .rootCause(ExceptionUtils.getRootCauseMessage(exception)) //
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGenericException(RuntimeException exception, WebRequest request) {
        logger.error("RuntimeException: " + exception.getMessage(), exception);

        return ResponseEntity //
                .status(HttpStatus.INTERNAL_SERVER_ERROR) //
                .body(ErrorResponse.builder() //
                        .message(exception.getMessage()) //
                        .rootCause(ExceptionUtils.getRootCauseMessage(exception)) //
                        .build());
    }
}

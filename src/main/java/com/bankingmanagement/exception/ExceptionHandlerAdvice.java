package com.bankingmanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BankDetailsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBankException(BankDetailsNotFoundException ex, HttpServletRequest request) {
        log.error("Bank details not found exception", ex);
        ExceptionResponse response = buildExceptionResponse(
            "BANK_NOT_FOUND",
            ex.getMessage(),
            request.getRequestURI(),
            HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        log.error("Validation failed: {}", errorMessage);
        ExceptionResponse response = buildExceptionResponse(
            "VALIDATION_FAILED",
            errorMessage,
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error occurred", ex);
        ExceptionResponse response = buildExceptionResponse(
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred. Please try again later.",
            request.getRequestURI(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionResponse buildExceptionResponse(String errorCode, String message, String uri, HttpStatus status) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(errorCode);
        response.setErrorMessage(message);
        response.setRequestedURI(uri);
        response.setStatus(status.value());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
}

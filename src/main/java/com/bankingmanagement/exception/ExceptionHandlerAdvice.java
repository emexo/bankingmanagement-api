package com.bankingmanagement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(BankDetailsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBankDetailsNotFound(WebRequest webRequest, BankDetailsNotFoundException ex) {
        log.warn("BankDetailsNotFoundException: {}", ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        // WebRequest#getDescription(false) typically returns something like "uri=/path" - store that
        response.setUri(webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(WebRequest webRequest, Exception ex) {
        log.error("Unhandled exception occurred for request {}", webRequest.getDescription(false), ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setUri(webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}

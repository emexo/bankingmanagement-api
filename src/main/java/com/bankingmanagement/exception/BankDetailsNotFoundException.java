package com.bankingmanagement.exception;

public class BankDetailsNotFoundException extends Exception{
    public BankDetailsNotFoundException(String message) {
        super(message);
    }

    public BankDetailsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.bankingmanagement.exception;

public class BankDetailsNotFoundException extends Exception{
    public BankDetailsNotFoundException(){
        super();
    }

    public BankDetailsNotFoundException(String msg){
        super(msg);
    }
}

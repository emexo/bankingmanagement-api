package com.bankingmanagement.exception;

import com.bankingmanagement.model.BankTO;

public class BankDetailsNotfoundException extends Exception{
    public BankDetailsNotfoundException(){
        super();
    }

    public BankDetailsNotfoundException(String msg){
        super(msg);
    }
}

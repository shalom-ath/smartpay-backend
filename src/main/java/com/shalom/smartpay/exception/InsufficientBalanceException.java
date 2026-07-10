package com.shalom.smartpay.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message){

        super(message);
    }
}

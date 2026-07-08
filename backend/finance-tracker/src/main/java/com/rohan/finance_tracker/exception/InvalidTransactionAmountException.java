package com.rohan.finance_tracker.exception;

public class InvalidTransactionAmountException extends RuntimeException{

    public InvalidTransactionAmountException(String message){
        super(message);
    }
}

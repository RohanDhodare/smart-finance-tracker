package com.rohan.finance_tracker.exception;

public class InvalidCredsException extends RuntimeException{
    public InvalidCredsException(String message){
        super(message);
    }
}

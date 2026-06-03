package com.rohan.finance_tracker.exception;

public class UsernameAlreadyExistsException extends RuntimeException{

    public UsernameAlreadyExistsException(String message){
//        here we use super() to send message to parent class i.e. RuntimeException
//        so that in globalException handler when we so ex.getMessage() it actually works
//        cause those are the inbuilt methods of the exception class

        super(message);
    }
}

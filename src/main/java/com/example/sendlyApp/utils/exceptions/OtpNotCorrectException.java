package com.example.sendlyApp.utils.exceptions;

public class OtpNotCorrectException extends RuntimeException{
    public OtpNotCorrectException(String message){
        super(message);
    }
}

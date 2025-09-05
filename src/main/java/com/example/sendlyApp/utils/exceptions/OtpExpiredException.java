package com.example.sendlyApp.utils.exceptions;

public class OtpExpiredException extends RuntimeException{
    public  OtpExpiredException(String message){
        super(message);
    }
}

package com.vijay.vz.exception;
// Custom Exception for Bad Request
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }
}

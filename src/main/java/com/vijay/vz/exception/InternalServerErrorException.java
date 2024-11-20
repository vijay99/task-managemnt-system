package com.vijay.vz.exception;
// Custom Exception for Internal Server Error
public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException(String message){
        super(message);
    }
}

package com.vijay.vz.exception;

// Custom Exception for Resource Not Found
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }

}

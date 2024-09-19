package com.rr.sociable.exception;

public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(String message){
        super(message);
    }
}

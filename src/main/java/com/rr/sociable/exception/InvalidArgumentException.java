package com.rr.sociable.exception;

public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(){
        super();
    }

    public InvalidArgumentException(String message){
        super(message);
    }
}

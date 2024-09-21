package com.rr.sociable.exception;

public class UserAlreadyInGroupException extends RuntimeException{
    public UserAlreadyInGroupException() {
        super();
    }

    public UserAlreadyInGroupException(String message) {
        super(message);
    }

    public UserAlreadyInGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}

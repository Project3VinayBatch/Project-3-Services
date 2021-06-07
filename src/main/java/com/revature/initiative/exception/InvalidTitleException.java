package com.revature.initiative.exception;

public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String message) {
        super(message);
    }
    public InvalidTitleException() { }
}
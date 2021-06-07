package com.revature.initiative.exception;

public class FileException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileException(final String message) {
        super(message);
    }
}

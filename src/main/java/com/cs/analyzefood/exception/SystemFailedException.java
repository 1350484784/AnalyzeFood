package com.cs.analyzefood.exception;

public class SystemFailedException extends RuntimeException {
    public SystemFailedException() {
        super();
    }

    public SystemFailedException(String message) {
        super(message);
    }

    public SystemFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemFailedException(Throwable cause) {
        super(cause);
    }
}

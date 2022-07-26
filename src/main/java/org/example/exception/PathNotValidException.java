package org.example.exception;

public class PathNotValidException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Path not valid!";

    public PathNotValidException() {
        super(DEFAULT_MESSAGE);
    }

    public PathNotValidException(String message) {
        super(message);
    }
}

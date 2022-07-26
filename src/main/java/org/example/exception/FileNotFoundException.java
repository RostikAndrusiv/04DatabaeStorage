package org.example.exception;

public class FileNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "File not found!";

    public FileNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}

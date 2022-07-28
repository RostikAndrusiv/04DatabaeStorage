package org.example.exception;

public class FileSizeException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "File is too big!";

    public FileSizeException() {
        super(DEFAULT_MESSAGE);
    }

    public FileSizeException(String message) {
        super(message);
    }
}

package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class AlreadyLoginException extends RuntimeException {
    public AlreadyLoginException(String message) {
        super(message);
    }
}

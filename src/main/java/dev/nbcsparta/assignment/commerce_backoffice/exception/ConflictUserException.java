package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class ConflictUserException extends RuntimeException {
    public ConflictUserException(String message) {
        super(message);
    }
}
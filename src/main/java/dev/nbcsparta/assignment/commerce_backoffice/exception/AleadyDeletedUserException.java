package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class AleadyDeletedUserException extends RuntimeException {
    public AleadyDeletedUserException(String message) {
        super(message);
    }
}

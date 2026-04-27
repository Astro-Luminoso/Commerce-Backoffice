package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

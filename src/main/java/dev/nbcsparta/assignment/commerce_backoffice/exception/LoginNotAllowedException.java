package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class LoginNotAllowedException extends RuntimeException {
    public LoginNotAllowedException(String message) {
        super(message);
    }
}

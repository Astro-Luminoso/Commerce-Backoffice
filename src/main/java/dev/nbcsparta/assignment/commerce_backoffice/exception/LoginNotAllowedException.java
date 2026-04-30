package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class LoginNotAllowedException extends ServiceException {
    public LoginNotAllowedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

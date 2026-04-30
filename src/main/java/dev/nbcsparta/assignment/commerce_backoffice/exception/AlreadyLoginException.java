package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class AlreadyLoginException extends ServiceException {
    public AlreadyLoginException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}

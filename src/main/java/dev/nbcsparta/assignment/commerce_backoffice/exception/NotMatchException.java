package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class NotMatchException extends ServiceException {
    public NotMatchException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}

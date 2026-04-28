package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class AlreadyDeletedUserException extends ServiceException {
    public AlreadyDeletedUserException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

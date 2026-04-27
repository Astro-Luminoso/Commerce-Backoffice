package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class AleadyDeletedUserException extends ServiceException {
    public AleadyDeletedUserException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

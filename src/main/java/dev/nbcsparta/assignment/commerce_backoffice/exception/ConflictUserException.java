package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class ConflictUserException extends ServiceException {
    public ConflictUserException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
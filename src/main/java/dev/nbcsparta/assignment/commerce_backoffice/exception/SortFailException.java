package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class SortFailException extends ServiceException{
    public SortFailException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

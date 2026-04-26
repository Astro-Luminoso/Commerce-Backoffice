package dev.nbcsparta.assignment.commerce_backoffice.config;

import org.springframework.http.HttpStatus;

public class SortFailException extends ProductException{
    public SortFailException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

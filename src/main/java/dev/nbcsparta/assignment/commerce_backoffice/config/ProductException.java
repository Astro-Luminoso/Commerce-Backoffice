package dev.nbcsparta.assignment.commerce_backoffice.config;


import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException{

    private final HttpStatus status;

    public ProductException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

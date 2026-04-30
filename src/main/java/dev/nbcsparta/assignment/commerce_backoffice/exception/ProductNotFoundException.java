package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ServiceException{
    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다.");
    }
}

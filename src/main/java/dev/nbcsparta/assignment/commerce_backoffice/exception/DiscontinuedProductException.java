package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class DiscontinuedProductException extends ServiceException {
    public DiscontinuedProductException() {
        super(HttpStatus.BAD_REQUEST, "단종된 상품입니다.");
    }
}

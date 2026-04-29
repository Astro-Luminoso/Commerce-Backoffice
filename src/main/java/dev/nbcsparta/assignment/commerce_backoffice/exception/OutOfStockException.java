package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class OutOfStockException extends ServiceException {
    public OutOfStockException() {
        super(HttpStatus.BAD_REQUEST, "재고가 부족합니다");
    }
}

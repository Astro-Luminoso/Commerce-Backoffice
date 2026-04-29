package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class TryBuyDiscontinuedProductException extends ServiceException {
    public TryBuyDiscontinuedProductException() {
        super(HttpStatus.BAD_REQUEST, "해당 상품은 단종 상태입니다.");
    }
}

package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class TryBuyNotAllowedProductException extends ServiceException {
    public TryBuyNotAllowedProductException() {
        super(HttpStatus.BAD_REQUEST, "해당 상품은 구매할 수 없는 상태입니다.");
    }
}

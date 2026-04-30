package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class AlreadyProcessingOrderException extends ServiceException {
    public AlreadyProcessingOrderException() {
        super(HttpStatus.BAD_REQUEST, "이미 배송중인 상품은 취소가 불가능합니다.");
    }
}

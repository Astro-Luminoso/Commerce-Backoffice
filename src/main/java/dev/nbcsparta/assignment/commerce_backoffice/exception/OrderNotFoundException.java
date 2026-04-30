package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends ServiceException{
    public OrderNotFoundException() { super(HttpStatus.NOT_FOUND, "주문정보를 찾을 수 없습니다."); }
}

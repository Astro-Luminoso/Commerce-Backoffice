package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.hibernate.service.spi.ServiceException;

public class OutOfStockException extends ServiceException {
    public OutOfStockException() {
        super("재고가 부족합니다");
    }
}

package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class ManagerNotFoundException extends ServiceException {
    public ManagerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 관리자입니다.");
    }
}

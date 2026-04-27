package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class AccessForbiddenException extends ServiceException {
    public AccessForbiddenException() {
        super(HttpStatus.FORBIDDEN, "해당 관리자는 접근권한이 없습니다.");
    }
}

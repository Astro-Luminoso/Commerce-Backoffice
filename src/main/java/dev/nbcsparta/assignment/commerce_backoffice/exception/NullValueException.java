package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class NullValueException extends ServiceException {
    public NullValueException() {
        super(HttpStatus.BAD_REQUEST, "DENIED 상태로 변경하려면 이유를 입력해야 합니다.");
    }
}

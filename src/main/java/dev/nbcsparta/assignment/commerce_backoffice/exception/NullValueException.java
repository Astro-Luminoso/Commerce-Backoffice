package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class NullValueException extends RuntimeException {
    public NullValueException() {
        super("DENIED 상태로 변경하려면 이유를 입력해야 합니다.");
    }
}

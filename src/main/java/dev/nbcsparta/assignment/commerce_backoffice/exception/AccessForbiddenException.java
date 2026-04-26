package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class AccessForbiddenException extends RuntimeException {
    public AccessForbiddenException() {
        super("해당 관리자는 접근권한이 없습니다.");
    }
}

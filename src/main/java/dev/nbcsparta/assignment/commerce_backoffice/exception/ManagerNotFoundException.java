package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class ManagerNotFoundException extends RuntimeException {
    public ManagerNotFoundException() {
        super("존재하지 않는 관리자입니다.");
    }
}

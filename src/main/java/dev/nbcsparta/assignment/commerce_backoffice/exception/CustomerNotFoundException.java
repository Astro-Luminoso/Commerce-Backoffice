package dev.nbcsparta.assignment.commerce_backoffice.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("존재하지 않는 고객입니다.");
    }
}

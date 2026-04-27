package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends ServiceException {
    public RoleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

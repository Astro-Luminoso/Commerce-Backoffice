package dev.nbcsparta.assignment.commerce_backoffice.exception;

import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends ServiceException {
    public ReviewNotFoundException() { super(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."); }
}

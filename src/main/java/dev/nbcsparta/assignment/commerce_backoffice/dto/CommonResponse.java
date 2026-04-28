package dev.nbcsparta.assignment.commerce_backoffice.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record CommonResponse<T>(int statusCode, String message, T data) {

    public static <T> CommonResponse<T> success(HttpStatus statusCode, String message, T data) {
        return new CommonResponse<>(statusCode.value(), message, data);
    }

    public static CommonResponse<Void> success(HttpStatus statusCode, String message) {
        return new CommonResponse<>(statusCode.value(), message, null);
    }

    public static CommonResponse<Void> fail(HttpStatus statusCode, String message) {
        return new CommonResponse<>(statusCode.value(), message, null);
    }

    public ResponseEntity<CommonResponse<T>> toResponseEntity() {
        return ResponseEntity.status(this.statusCode).body(this);
    }
}


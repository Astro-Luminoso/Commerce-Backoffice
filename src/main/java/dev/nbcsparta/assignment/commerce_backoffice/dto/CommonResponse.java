package dev.nbcsparta.assignment.commerce_backoffice.dto;

import org.springframework.http.HttpStatus;

public record CommonResponse<T>(int statusCode, String message, T data) {

    public static <T> CommonResponse<T> success(HttpStatus statusCode, String message, T data) {
        return new CommonResponse<>(statusCode.value(), message, data);
    }

    public static <T> CommonResponse<T> success(HttpStatus statusCode, String message) {
        return new CommonResponse<>(statusCode.value(), message, null);
    }

    public static <T> CommonResponse<T> fail(HttpStatus statusCode, String message) {
        return new CommonResponse<>(statusCode.value(), message, null);
    }
}


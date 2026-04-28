package dev.nbcsparta.assignment.commerce_backoffice.exception;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CommonResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 커스텀 예외 핸들링
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<CommonResponse<Void>> handleServiceException(ServiceException ex) {
        return CommonResponse.fail(ex.getStatus(), ex.getMessage()).toResponseEntity();
    }

    // Bean Validation 예외 핸들링
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("입력 값이 올바르지 않습니다.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}

package dev.nbcsparta.assignment.commerce_backoffice.exception;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CommonResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    public ResponseEntity<CommonResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("입력 값이 올바르지 않습니다.");

        return CommonResponse.fail(HttpStatus.BAD_REQUEST, errorMessage).toResponseEntity();
    }

    // ====================================================================================
    //                                  기타 예외 핸들링
    // ====================================================================================

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        return CommonResponse.fail(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.").toResponseEntity();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return CommonResponse.fail(HttpStatus.CONFLICT, "데이터 무결성 위반").toResponseEntity();
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CommonResponse<Void>> handleJwtException(JwtException ex) {
        return CommonResponse.fail(HttpStatus.UNAUTHORIZED, ex.getMessage()).toResponseEntity();
    }
}

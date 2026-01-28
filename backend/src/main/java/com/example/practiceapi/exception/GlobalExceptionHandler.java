package com.example.practiceapi.exception;

import com.example.practiceapi.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 業務例外（BusinessException とその子クラス）
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorResponse error = ErrorResponse.of(
            e.getStatus(),
            e.getError(),
            e.getMessage()
        );
        return ResponseEntity.status(e.getStatus()).body(error);
    }

    // バリデーションエラー（@Valid で検証失敗）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(error -> error.getDefaultMessage())
            .orElse("入力値が不正です");

        ErrorResponse error = ErrorResponse.of(400, "Bad Request", message);
        return ResponseEntity.status(400).body(error);
    }

    // その他の例外（想定外のエラー）
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse error = ErrorResponse.of(
            500,
            "Internal Server Error",
            "予期しないエラーが発生しました"
        );
        return ResponseEntity.status(500).body(error);
    }
}

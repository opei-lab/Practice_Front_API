package com.example.practiceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private int status;              // HTTPステータスコード
    private String error;            // エラー種別（"Bad Request" 等）
    private String message;          // エラーメッセージ
    private LocalDateTime timestamp; // 発生日時

    // メッセージだけで作れる簡易コンストラクタ
    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(status, error, message, LocalDateTime.now());
    }
}

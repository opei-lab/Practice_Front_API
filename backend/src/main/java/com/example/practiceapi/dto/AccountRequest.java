package com.example.practiceapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequest {

    @NotBlank(message = "ユーザー名は必須です")
    @Size(max = 50, message = "50文字以内で入力してください")
    private String username;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メール形式が不正です")
    private String email;
}

package com.example.practiceapi.service;

import com.example.practiceapi.dto.LoginRequest;
import com.example.practiceapi.entity.Account;
import com.example.practiceapi.exception.AuthenticationException;
import com.example.practiceapi.exception.ResourceNotFoundException;
import com.example.practiceapi.repository.AccountRepository;
import com.example.practiceapi.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * ログイン処理
     */
    public String login(LoginRequest request) {
        // 1. ユーザー名でアカウント検索
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("ユーザーが見つかりません"));

        // 2. パスワード照合（BCryptで比較）
        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new AuthenticationException("パスワードが間違っています");
        }

        // 3. JWTトークンを生成して返す
        return jwtTokenProvider.generateToken(account.getUsername());
    }
}

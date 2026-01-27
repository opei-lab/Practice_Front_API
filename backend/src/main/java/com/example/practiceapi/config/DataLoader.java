package com.example.practiceapi.config;

import com.example.practiceapi.entity.Account;
import com.example.practiceapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 既にデータがあれば何もしない
        if (accountRepository.count() > 0) {
            return;
        }

        // テストデータを登録（パスワードは正しくハッシュ化）
        String encodedPassword = passwordEncoder.encode("password123");

        Account taro = new Account();
        taro.setUsername("taro");
        taro.setEmail("taro@example.com");
        taro.setPassword(encodedPassword);
        accountRepository.save(taro);

        Account hanako = new Account();
        hanako.setUsername("hanako");
        hanako.setEmail("hanako@example.com");
        hanako.setPassword(encodedPassword);
        accountRepository.save(hanako);

        Account jiro = new Account();
        jiro.setUsername("jiro");
        jiro.setEmail("jiro@example.com");
        jiro.setPassword(encodedPassword);
        accountRepository.save(jiro);

        System.out.println("テストデータを登録しました（password: password123）");
    }
}

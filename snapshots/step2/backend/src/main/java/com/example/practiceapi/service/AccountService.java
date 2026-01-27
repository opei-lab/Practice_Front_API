package com.example.practiceapi.service;

import com.example.practiceapi.dto.AccountRequest;
import com.example.practiceapi.entity.Account;
import com.example.practiceapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account save(AccountRequest request) {
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        return accountRepository.save(account);
    }
}

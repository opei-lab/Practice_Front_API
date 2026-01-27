package com.example.practiceapi.repository;

import com.example.practiceapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // JpaRepositoryを継承するだけで基本CRUD(findAll, findById, save, delete等)が使える

    // ★追加: ユーザー名でアカウント検索
    // メソッド名から自動でSQLが生成される（Spring Data JPAの機能）
    // → SELECT * FROM accounts WHERE username = ?
    Optional<Account> findByUsername(String username);
}

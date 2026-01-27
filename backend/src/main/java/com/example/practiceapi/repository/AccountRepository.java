package com.example.practiceapi.repository;

import com.example.practiceapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // JpaRepositoryを継承するだけで基本CRUD(findAll, findById, save, delete等)が使える
}

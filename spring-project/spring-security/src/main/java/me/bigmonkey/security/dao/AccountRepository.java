package me.bigmonkey.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import me.bigmonkey.security.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserName(String username);
}

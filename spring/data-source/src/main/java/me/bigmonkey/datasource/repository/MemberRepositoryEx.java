package me.bigmonkey.datasource.repository;

import java.sql.SQLException;

import me.bigmonkey.datasource.domain.Member;

public interface MemberRepositoryEx {

    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;
}
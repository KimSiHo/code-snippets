package me.bigmoneky.mybatis.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import me.bigmoneky.mybatis.entities.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}

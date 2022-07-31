package me.bigmoneky.mybatis;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.bigmoneky.mybatis.dao.jpa.MemberJpaRepository;
import me.bigmoneky.mybatis.entities.Member;

@Order(1)
@RequiredArgsConstructor
@Component
public class DataInsertRunner implements ApplicationRunner {

    private final MemberJpaRepository memberJpaRepository;
    private final EntityManager em;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member build = Member.builder()
            .memberNo(1L)
            .name("test")
            .registerDate(LocalDateTime.now())
            .build();

        memberJpaRepository.save(build);
    }
}

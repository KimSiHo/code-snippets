package me.bigmoneky.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.bigmoneky.mybatis.dao.mybatis.MemberMapper;
import me.bigmoneky.mybatis.dto.MemberResponse;

@Slf4j
@Order(2)
@Component
public class TestRunner implements ApplicationRunner {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.error("error test");
        log.warn("warn test");
        log.info("info test");
        log.debug("debug test");
        log.trace("trace test");

        MemberResponse member = memberMapper.getMember(1L);
        System.out.println(member);
    }
}

package me.bigmoneky.mybatis.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.bigmoneky.mybatis.dao.mybatis.MemberMapper;
import me.bigmoneky.mybatis.dto.MemberResponse;

@RequiredArgsConstructor
@RestController
public class HelloController {

    private final MemberMapper memberMapper;

    @RequestMapping("/test")
    public void hello() {
        final MemberResponse member = memberMapper.getMember(1L);
        System.out.println(member);
    }

}

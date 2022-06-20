package com.example.jpaentity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/jpa")
@RestController
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/manyToOne")
    public String test01() {
        helloService.doTest01();
        return "success";
    }

    @GetMapping("/oneToMany")
    public String test02() {
        helloService.doTest02();
        return "success";
    }

    @GetMapping("/oneToOne")
    public String test03() {
        helloService.doTest03();
        return "success";
    }
}

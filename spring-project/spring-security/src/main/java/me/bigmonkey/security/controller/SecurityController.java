package me.bigmonkey.security.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/security")
@Controller
public class SecurityController {

    @ResponseBody
    @GetMapping("/filter/list")
    public String test() {
        return "for see filter chain proxy";
    }

    public void test2(HttpSession session) {

    }

}

package me.bigmonkey.security.controller;

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

}

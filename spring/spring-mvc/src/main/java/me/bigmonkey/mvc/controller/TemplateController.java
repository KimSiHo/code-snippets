package me.bigmonkey.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TemplateController {

    private final TemplateEngine templateEngine;

    @GetMapping("/thymeleaf/test15")
    public String thymeleafTest15() {
        System.out.println("hi");
        return "fragment/fragmentMain";
    }

    @GetMapping("/thymeleaf/test16")
    public String thymeleafTest16() {
        return "fragment/layoutMain";
    }

    @GetMapping("/thymeleaf/test17")
    public String thymeleafTest17() {
        return "fragment/layoutExtendMain";
    }

}

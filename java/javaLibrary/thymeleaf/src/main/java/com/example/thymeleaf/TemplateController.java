package com.example.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TemplateController {

    private final TemplateEngine templateEngine;

    @GetMapping("/thymeleaf/fragment/main")
    public String thymeleafTest15() {
        return "fragment/fragmentMain";
    }

    @GetMapping("/thymeleaf/layout/main")
    public String thymeleafTest16() {
        return "fragment/layout/layoutMain";
    }

    @GetMapping("/thymeleaf/layout/extend")
    public String thymeleafTest17() {
        return "fragment/layout2/layoutExtendMain";
    }

}

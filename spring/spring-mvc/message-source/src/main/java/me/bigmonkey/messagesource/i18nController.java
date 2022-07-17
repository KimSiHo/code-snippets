package me.bigmonkey.messagesource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class i18nController {

    @GetMapping("/i18n/html")
    public String i18Html() {
        return "i18n/test";
    }
}


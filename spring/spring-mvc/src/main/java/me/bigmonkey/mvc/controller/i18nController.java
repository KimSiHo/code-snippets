package me.bigmonkey.mvc.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class i18nController {

    @Lazy
    @Autowired
    MessageSource messageSource;

    @ResponseBody
    @GetMapping("/i18n/test1")
    public String i18Test1() {
        System.out.println(messageSource.getClass());
        System.out.println(messageSource.getMessage("greeting", new String[]{"siho2"}, Locale.US));
        System.out.println(messageSource.getMessage("greeting", new String[]{"siho"}, Locale.KOREA));

        System.out.println(messageSource.getMessage("no_code", new String[]{"siho2"}, "기본 메시지", Locale.KOREA));


        return "see log";
    }


    @GetMapping("/i18n/test2")
    public String i18Test2() {

        return "i18n/test1";
    }

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages", "classpath:/errors");

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3);

        return messageSource;
    }
}


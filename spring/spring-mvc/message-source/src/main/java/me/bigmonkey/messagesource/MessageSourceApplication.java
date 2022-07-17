package me.bigmonkey.messagesource;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class MessageSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageSourceApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        //Locale.setDefault(Locale.KOREA);

        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages", "classpath:/errors");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3);

        return messageSource;
    }
}

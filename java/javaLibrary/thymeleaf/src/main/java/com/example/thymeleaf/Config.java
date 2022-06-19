package com.example.thymeleaf;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class Config {

    private static final String MAIL_ENCODING = StandardCharsets.UTF_8.name();

    @Bean
    public SpringResourceTemplateResolver templateResolverHtml() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/mail/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(MAIL_ENCODING);
        resolver.setOrder(1);
        resolver.setCheckExistence(false); // 먼저 체크를 하지 않아서 실제 실행 중에 fileNotFount 예외가 발생하고 조금 더 자세한 에러를 봐서 문제를 해결할 수 있었다

        return resolver;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolverTxt() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/mail/");
        resolver.setSuffix(".txt");
        resolver.setTemplateMode(TemplateMode.TEXT);
        resolver.setCharacterEncoding(MAIL_ENCODING);
        resolver.setOrder(2);
        resolver.setCheckExistence(false);

        return resolver;
    }
}

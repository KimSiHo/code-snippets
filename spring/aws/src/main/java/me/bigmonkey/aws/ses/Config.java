package me.bigmonkey.aws.ses;

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
        resolver.setCheckExistence(true);

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
        resolver.setCheckExistence(true);

        return resolver;
    }
}

package me.bigmonkey.aws.ses;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@Component
public class SampleRunner implements ApplicationRunner {

    private final TemplateEngine templateEngine;
    private final SendEmailService sendEmailService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Context context = new Context();
        context.setVariable("name", "kimsiho");
        final String content = templateEngine.process("mail/dormant", context);
        log.info(content);

        sendEmailService.send("test 제목", content, List.of("kim125y@naver.com"));
    }
}
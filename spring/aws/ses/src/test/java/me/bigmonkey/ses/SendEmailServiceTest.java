package me.bigmonkey.ses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
class SendEmailServiceTest {

    private final SendEmailService sendEmailService;

    @Test
    void sendEmailTest() {
        sendEmailService.send("test", "hi", List.of("kim125y@naver.com"));
    }
}
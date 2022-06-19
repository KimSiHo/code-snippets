package me.bigmonkey.utils.regex;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class RegexRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        final String maskingEmail = RegexUtil.maskingEmail("kim125y@naver.com");
        System.out.println("maskingEmail = " + maskingEmail);

        final String maskingEmail2 = RegexUtil.maskingEmail("y@naver.com");
        System.out.println("maskingEmail2 = " + maskingEmail2);

        final String maskingEmail3 = RegexUtil.maskingEmail("yb@naver.com");
        System.out.println("maskingEmail3 = " + maskingEmail3);

        final String maskingEmail4 = RegexUtil.maskingEmail("ybc@naver.com");
        System.out.println("maskingEmail4 = " + maskingEmail4);

        // 구분
        final String maskingEmail5 = RegexUtil.maskingEmailN("kim125y@naver.com");
        System.out.println("maskingEmail5 = " + maskingEmail5);

        final String maskingEmail6 = RegexUtil.maskingEmailN("y@naver.com");
        System.out.println("maskingEmail6 = " + maskingEmail6);

        final String maskingEmail7 = RegexUtil.maskingEmailN("yb@naver.com");
        System.out.println("maskingEmail7 = " + maskingEmail7);

        final String maskingEmail8 = RegexUtil.maskingEmailN("ybc@naver.com");
        System.out.println("maskingEmail8 = " + maskingEmail8);

        final String maskingEmail9 = RegexUtil.maskingEmailN("@naver.com");
        System.out.println("maskingEmail9 = " + maskingEmail9);
    }
}
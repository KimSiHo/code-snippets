package me.bigmonkey.profile;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TestRunner implements ApplicationRunner {

    private final Environment env;

    @Value("${test.hi}")
    String cur;

    @Value("${test.default}")
    String test;

    @Value("${test.prod}")
    String prod;

    @Value("${test.secret}")
    String secret;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(Arrays.toString(env.getDefaultProfiles()));
        System.out.println(Arrays.toString(env.getActiveProfiles()));
        System.out.println("cur = " + cur);
        System.out.println("test = " + test);
        System.out.println("prod = " + prod);
        System.out.println("secret = " + secret);
    }
}

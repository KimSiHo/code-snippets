package com.example.various.cbiding;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TestRunner implements ApplicationRunner {

    private final HttpClientProperties httpClientProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("========================");
        System.out.println(httpClientProperties.getConnectRequestTimeout());
        System.out.println(httpClientProperties.getConnectTimeout());
        System.out.println(httpClientProperties.getMaxConnectPerRoute());
        System.out.println(httpClientProperties.getResponseTimeout());
    }
}

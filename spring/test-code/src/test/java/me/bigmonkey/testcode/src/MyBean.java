package me.bigmonkey.testcode.src;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBean {

    public void hello() {
        System.out.println("this is from my bean!");
    }
}

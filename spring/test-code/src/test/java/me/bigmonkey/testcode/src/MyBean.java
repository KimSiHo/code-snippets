package me.bigmonkey.testcode.src;

import org.springframework.stereotype.Component;

@Component
public class MyBean {

    public void hello() {
        System.out.println("this is from my bean!");
    }
}

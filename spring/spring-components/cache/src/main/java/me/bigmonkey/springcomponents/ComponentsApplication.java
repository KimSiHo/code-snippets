package me.bigmonkey.springcomponents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ComponentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComponentsApplication.class, args);
    }
}

/*
package me.bigmonkey.structure;

import java.util.TimeZone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

*/
/**
 * TODO base packages 확인 필요
 *//*

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.test.*")
public class MemberApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MemberApplication.class)
                .listeners(new MemberApplicationListener(args))
                .run(args);
    }

    @EventListener
    public void event(ApplicationEvent event) {
        TimeZone.setDefault(TimeZone.getTimeZone("Portugal"));
        log.debug("ApplicationEvent: {}", event.getClass().getSimpleName());
    }
}
*/

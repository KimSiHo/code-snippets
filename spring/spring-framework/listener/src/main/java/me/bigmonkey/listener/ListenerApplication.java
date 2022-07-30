package me.bigmonkey.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ListenerApplication {

    /*ApplicationEvent: ApplicationContextInitializedEvent
    ApplicationEvent: ApplicationPreparedEvent
    ApplicationEvent: ServletWebServerInitializedEvent
    ApplicationEvent: ContextRefreshedEvent
    ApplicationEvent: ApplicationStartedEvent
    ApplicationEvent: AvailabilityChangeEvent
    ApplicationEvent: ApplicationReadyEvent
    ApplicationEvent: AvailabilityChangeEvent*/

    public static void main(String[] args) {
        new SpringApplicationBuilder(ListenerApplication.class)
            //.listeners(new ListenerApplicationListener())
            .run(args);
    }

    @EventListener
    public void event(ApplicationEvent event) {
        log.info("ApplicationEvent: {}", event.getClass().getSimpleName());
    }

    /*ApplicationEvent: ServletWebServerInitializedEvent
    ApplicationEvent: ContextRefreshedEvent
    ApplicationEvent: ApplicationStartedEvent
    ApplicationEvent: AvailabilityChangeEvent
    ApplicationEvent: ApplicationReadyEvent
    ApplicationEvent: AvailabilityChangeEvent*/
}

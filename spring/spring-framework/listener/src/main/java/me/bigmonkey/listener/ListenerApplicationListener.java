package me.bigmonkey.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListenerApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //log.info("ApplicationEvent: {}", event.getClass().getSimpleName());

        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            new LoadAdditionalProperties().additionalProperties((ApplicationEnvironmentPreparedEvent) event);
        }
    }
}

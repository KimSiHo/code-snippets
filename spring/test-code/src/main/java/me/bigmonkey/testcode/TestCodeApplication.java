package me.bigmonkey.testcode;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TestCodeApplication {

    // 리스너는 작동 안함
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestCodeApplication.class)
            //.listeners(new TestCodeApplicationListener())
            .run(args);
    }

    /*ApplicationEvent: ContextRefreshedEvent
    ApplicationEvent: ApplicationStartedEvent
    ApplicationEvent: AvailabilityChangeEvent
    ApplicationEvent: ApplicationReadyEvent
    ApplicationEvent: AvailabilityChangeEvent
    ApplicationEvent: PrepareTestInstanceEvent
    ApplicationEvent: BeforeTestMethodEvent
    ApplicationEvent: BeforeTestExecutionEvent
    ApplicationEvent: AfterTestExecutionEvent
    ApplicationEvent: AfterTestMethodEvent
    ApplicationEvent: AfterTestClassEvent
    ApplicationEvent: ContextClosedEvent*/
    //@EventListener
    public void event(ApplicationEvent event) {
        log.info("ApplicationEvent: {}", event.getClass().getSimpleName());
    }
}

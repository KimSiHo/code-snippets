package me.bigmonkey.aws.sns.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSns;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.sns.message.SnsMessageManager;

@EnableSns
@Configuration
@EnableAutoConfiguration(exclude = {ContextInstanceDataAutoConfiguration.class})
public class AwsConfiguration {

    // for SNS Notification handler
    @Bean
    SnsMessageManager snsMessageManager() {
        return new SnsMessageManager();
    }
}
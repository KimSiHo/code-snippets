package me.bigmonkey.testcode;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.testcontainers.shaded.org.apache.commons.lang3.ArrayUtils;

@Configuration
public class PropertyTestConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(ArrayUtils.addAll(
            new PathMatchingResourcePatternResolver().getResources("classpath:config/*.yml"),
            new PathMatchingResourcePatternResolver().getResources("classpath:config-dev/*.yml")
        ));

        return configurer;
    }

}

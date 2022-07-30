package me.bigmonkey.testcode;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadAdditionalProperties {

    private static final String ADDITIONAL_LOCATION_PATTERN = "classpath:config/*.yml";

    @SneakyThrows
    public void additionalProperties(ApplicationEnvironmentPreparedEvent event) {
        final YamlPropertySourceLoader propertyLoader = new YamlPropertySourceLoader();

        //String profile = System.getProperty("spring.profiles.active");
        String profile = "dev";
        log.info("spring.profiles.active = {}", profile);

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(ADDITIONAL_LOCATION_PATTERN);
        Resource[] profileResources = new PathMatchingResourcePatternResolver().getResources("classpath:config-" + profile + "/*.yml");
        for (Resource resource : resources) {
            try {
                final PropertySource<?> propertySource = propertyLoader.load(resource.getFilename(), resource).get(0);
                event.getEnvironment().getPropertySources().addLast(propertySource);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load yaml configuration + " + resource, e);
            }
        }

        for (Resource resource : profileResources) {
            try {
                final PropertySource<?> propertySource = propertyLoader.load(resource.getFilename(), resource).get(0);
                event.getEnvironment().getPropertySources().addLast(propertySource);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load yaml configuration + " + resource, e);
            }
        }
    }
}

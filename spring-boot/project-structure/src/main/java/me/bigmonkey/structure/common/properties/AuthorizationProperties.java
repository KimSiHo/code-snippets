package me.bigmonkey.structure.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "authorization")
public class AuthorizationProperties {
    private final String host;
    private final AuthorizationUrlsProperties urls;
}

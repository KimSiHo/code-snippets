package me.bigmonkey.structure.common.properties;

import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
public class AuthorizationUrlsProperties {

    private final String helloWorld;
    private final String token;
}

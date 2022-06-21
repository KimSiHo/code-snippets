package me.bigmonkey.structure.dao.redis;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AccessTokenRedisCache {
    @Cacheable(value = "AT", key = "#accessToken", unless="#result == null")
    public Optional<String> selectAccessToken(String accessToken) {
        return Optional.empty();
    }
}

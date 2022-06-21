package me.bigmonkey.structure.dao.redis;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.etoos.member.common.cache.RedisCacheNames;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChangePhoneIdRedisCache {
    @Cacheable(value = RedisCacheNames.CHANGE_PHONE_ID, key = "#id", unless="#result == null")
    public Optional<Boolean> select(String id) {
        return Optional.empty();
    }

    @CachePut(value = RedisCacheNames.CHANGE_PHONE_ID, key = "#id")
    public Boolean save(String id, Boolean value) {
        return value;
    }

    @CacheEvict(value = RedisCacheNames.CHANGE_PHONE_ID, key = "#id")
    public void remove(String id) {
        log.info("remove check phone id : {}", id);
    }
}
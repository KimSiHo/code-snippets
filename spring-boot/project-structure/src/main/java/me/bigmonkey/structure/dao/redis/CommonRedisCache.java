package me.bigmonkey.structure.dao.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommonRedisCache {

    private final StringRedisTemplate redisTemplate;
    private static ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    public <T> Optional<T> getHash(String key, String hashKey, Class<T> type) throws JsonProcessingException {
        Object o = redisTemplate.opsForHash().get(key, hashKey);
        if (o == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(objectMapper.readValue((String)o, type));
        }
    }

    public <T> void saveHash(String key, String hashKey, Object object) {
        redisTemplate.opsForHash().put(key, hashKey, object);
    }

    public <T> List<T> getAllHash(String key, Class<T> type) throws JsonProcessingException {

        List<T> list = new ArrayList<>();
        List<Object> values = redisTemplate.opsForHash().values(key);
        if (values != null && !values.isEmpty()) {
            for (Object o : values) {
                T t = objectMapper.readValue((String)o, type);
                list.add(t);
            }
        }
        return list;
    }

    public boolean isEmpty(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public void removeHash(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public boolean deleteKey(String key) {
        Boolean delete = redisTemplate.delete(key);
        log.info("commonRedisCache.deleteKey key = {} , deleted = {}", key, delete);
        return delete;
    }

}

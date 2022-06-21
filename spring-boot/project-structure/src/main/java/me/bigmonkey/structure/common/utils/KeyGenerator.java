package me.bigmonkey.structure.common.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

// 필요한 건지는 잘 모르겠지만 일단..
@Slf4j
@UtilityClass
public class KeyGenerator {
    public static final String REDIS_CACHE_KEY_SEPARATOR = ":";

    static public String createKey(String str1, String str2) {
        String createdKey = str1 + REDIS_CACHE_KEY_SEPARATOR + str2;
        log(createdKey);
        return createdKey;
    }

    static public String createKey(String str1, String str2, String str3) {
        String createdKey = str1 + REDIS_CACHE_KEY_SEPARATOR + str2 + REDIS_CACHE_KEY_SEPARATOR + str3;
        log(createdKey);
        return createdKey;
    }

    static public String createKey(String str1, String str2, String str3, String str4) {
        String createdKey = str1 + REDIS_CACHE_KEY_SEPARATOR + str2 + REDIS_CACHE_KEY_SEPARATOR + str3 + REDIS_CACHE_KEY_SEPARATOR + str4;
        log(createdKey);
        return createdKey;
    }

    static public String createKey(String... strs) {
        String createdKey = Arrays.stream(strs)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(REDIS_CACHE_KEY_SEPARATOR));
        log(createdKey);
        return createdKey;
    }

    private static void log(String key) {
        log.info("keyGenerator.createKey, key = {}", key);
    }

}

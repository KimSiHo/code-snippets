/*
 * @(#)CustomCacheErrorHandler 2021-11-23
 *
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.etoos.member.common.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 스프링 캐시 커스텀 error handler
 *
 * @author 김시호 kim125y@etoos.com
 * @since 2021-11-23
 */
@Slf4j
public class CustomCacheErrorHandler implements CacheErrorHandler {
    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error("REDIS ERROR : GET ERROR : " + key.toString() , exception);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.error("REDIS ERROR : PUT ERROR : " + key.toString() , exception);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error("REDIS ERROR : EVICT ERROR : " + key.toString() , exception);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error("REDIS ERROR : CLEAR ERROR", exception);
    }
}

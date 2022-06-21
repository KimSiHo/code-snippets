/*
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.etoos.member.common.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.etoos.member.dto.cache.BlockUser;
import com.etoos.member.dto.cache.PasswordToken;
import com.etoos.member.entities.member.Member;
import com.etoos.member.entities.member.ServiceProfile;
import com.etoos.member.entities.member.ServiceTermsAgree;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${cache.redis.time-to-live.join-token}")
    private Duration joinTokenLiveTime;

    @Value("${cache.redis.time-to-live.retry-token}")
    private Duration retryTokenLiveTime;

    @Value("${cache.redis.time-to-live.change-phone}")
    private Duration changePhoneLiveTime;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        final RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            //.disableCachingNullValues()
            .computePrefixWith(cacheName -> cacheName + RedisCacheNames.REDIS_CACHE_KEY_SEPARATOR)
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        final Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(RedisCacheNames.SIGN_UP_TOKEN, cacheConfiguration.entryTtl(joinTokenLiveTime));
        cacheConfigurations.put(RedisCacheNames.ETOOS_TOKEN, cacheConfiguration.entryTtl(joinTokenLiveTime));
        cacheConfigurations.put(RedisCacheNames.SEND_RETRY, cacheConfiguration.entryTtl(retryTokenLiveTime));
        cacheConfigurations.put(RedisCacheNames.CLIENT_INFO, cacheConfiguration.entryTtl(retryTokenLiveTime));
        cacheConfigurations.put(RedisCacheNames.CHANGE_PHONE_ID, cacheConfiguration.entryTtl(changePhoneLiveTime));

        cacheConfigurations.put(RedisCacheNames.MEMBER, cacheConfiguration.serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Member.class))));
        cacheConfigurations.put(RedisCacheNames.PASSWORD_TOKEN, cacheConfiguration.serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(PasswordToken.class))));
        cacheConfigurations.put(RedisCacheNames.SERVICE_PROFILE, cacheConfiguration.serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(ServiceProfile.class))));

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(connectionFactory)
            .cacheDefaults(cacheConfiguration)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build();
    }

    @Bean(name = "ServiceTermsRedisTemplate")
    public RedisTemplate<String, ServiceTermsAgree> serviceTermsRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ServiceTermsAgree> template = new RedisTemplate<>();
        template.setDefaultSerializer(RedisSerializer.string());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ServiceTermsAgree.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "ServiceObjectTemplate")
    public RedisTemplate<String, BlockUser> serviceObjectTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, BlockUser> template = new RedisTemplate<>();
        template.setDefaultSerializer(RedisSerializer.string());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(BlockUser.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "ServiceProfileRedisTemplate")
    public RedisTemplate<String, ServiceProfile> serviceProfileRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ServiceProfile> template = new RedisTemplate<>();
        template.setDefaultSerializer(RedisSerializer.string());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ServiceProfile.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "ServiceProfileEtcRedisTemplate")
    public RedisTemplate<String, String> serviceProfileEtcRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setDefaultSerializer(RedisSerializer.string());
//        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "MembersRedisTemplate")
    public RedisTemplate<String, String> membersRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setDefaultSerializer(RedisSerializer.string());
//        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean(name = "RedisObjectTemplate")
    public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return redisTemplate;
    }
}

package com.ame.cache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnClass(CacheManager.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CacheAutoConfiguration {

    @Configuration
    @ConditionalOnProperty(value = "ame.cache.provider", havingValue = "ehcache", matchIfMissing = true)
    @Import(EhCacheConfiguration.class)
    static class Ehcache3AutoConfiguration {

    }

//    @Configuration
//    @ConditionalOnProperty(value = "meper.cache.provider", havingValue = "redis")
//    @ConditionalOnBean(type = "org.springframework.data.redis.connection.RedisConnectionFactory")
//    @Import(RedisCacheConfiguration.class)
//    static class RedisCacheAutoConfiguration {
//
//    }
}

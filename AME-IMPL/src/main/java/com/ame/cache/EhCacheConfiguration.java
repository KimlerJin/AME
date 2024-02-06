package com.ame.cache;


import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(Ehcache3Properties.class)
public class EhCacheConfiguration {


    @Autowired
    private CacheProperties cacheProperties;

    @Autowired
    private Ehcache3Properties ehcache3Properties;

    @Bean
    public CacheManager ehcache3LCacheManager(org.ehcache.CacheManager cacheManager,
                                              CacheConfiguration<Object, CacheValueWrapper> defaultCacheConfiguration) {
        return new EhCache3Manager(cacheManager, defaultCacheConfiguration, ehcache3Properties);
    }

    @Bean
    public CacheConfiguration<Object, CacheValueWrapper> defaultCacheConfiguration() {
        return CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, CacheValueWrapper.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(ehcache3Properties.getCacheHeapSize(), MemoryUnit.MB)
                                .offheap(ehcache3Properties.getCacheOffHeapSize(), MemoryUnit.MB))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(cacheProperties.getTimeToLive())))
                .build();
    }

    @Bean
    public org.ehcache.CacheManager ehcache3CacheManager() {
        org.ehcache.CacheManager build = CacheManagerBuilder.newCacheManagerBuilder()
                .withSerializer(CacheValueWrapper.class, CacheValueWrapperSerializer.class)
                .withSerializer(Object.class, ObjectSerializer.class).build();
        build.init();
        return build;
    }
}

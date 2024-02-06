package com.ame.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "ame.cache.enable", havingValue = "true")
public class AMECacheConfiguration {

    @Bean
    public AMECacheMethodMatcherPointcutAdvisor ameCacheMethodMatcherPointcutAdvisor(CacheProcessor cacheProcessor) {
        return new AMECacheMethodMatcherPointcutAdvisor(cacheProcessor);
    }

//    @Bean
//    public CacheManager cacheManager() {
//        return new EhCache3Manager();
//    }


}

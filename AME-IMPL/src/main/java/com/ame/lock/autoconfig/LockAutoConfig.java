package com.ame.lock.autoconfig;


import com.ame.config.CommonsDataAutoConfiguration;
import com.ame.lock.LockDao;
import com.ame.lock.LockService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(CommonsDataAutoConfiguration.class)
@EntityScan("com.ame.lock")
public class LockAutoConfig {

    @Bean
    public LockDao lockDao() {
        return new LockDao();
    }

    @Bean
    public LockService lockService() {
        return new LockService();
    }

}

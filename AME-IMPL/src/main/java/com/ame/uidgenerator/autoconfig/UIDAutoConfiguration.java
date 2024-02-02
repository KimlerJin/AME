package com.ame.uidgenerator.autoconfig;

import com.ame.lock.LockService;
import com.ame.uidgenerator.DisposableWorkerIdAssigner;
import com.ame.uidgenerator.WorkerNodeDao;
import com.ame.uidgenerator.impl.CachedUidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(IntegrationAutoConfiguration.class)
@EnableConfigurationProperties(UIDProperties.class)
@EntityScan("com.ame.uidgenerator")
public class UIDAutoConfiguration {

    @Autowired
    private UIDProperties uidProperties;

    @Bean
    public WorkerNodeDao workerNodeDAO() {
        return new WorkerNodeDao();
    }

    @Bean
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner(WorkerNodeDao workerNodeDao, LockService lockService) {
        return new DisposableWorkerIdAssigner(workerNodeDao, lockService);
    }

    @Bean
    public CachedUidGenerator cachedUidGenerator() {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setEpochStr(uidProperties.getEpochStr());
        cachedUidGenerator.setTimeBits(uidProperties.getTimeBits());
        cachedUidGenerator.setWorkerBits(uidProperties.getWorkerBits());
        cachedUidGenerator.setSeqBits(uidProperties.getSeqBits());
        return cachedUidGenerator;
    }
}

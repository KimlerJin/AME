package com.ame.util.concurrent;

import com.ame.spring.BeanManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class BatchJobsServiceBuilder {

    private BatchJobsInvokeStrategy batchJobsInvokeStrategy = BatchJobsInvokeStrategy.COMPLETE_ALL_JOB;

    private Executor executor;

    private List<BatchJob> batchJobs = new ArrayList<>();

    protected BatchJobsServiceBuilder(Executor executor) {
        this.executor = executor;
    }

    protected BatchJobsServiceBuilder() {
    }

    public BatchJobsServiceBuilder jobInvokeStrategy(BatchJobsInvokeStrategy batchJobsInvokeStrategy) {
        this.batchJobsInvokeStrategy = batchJobsInvokeStrategy;
        return this;
    }

    public BatchJobsServiceBuilder job(BatchJob batchJob) {
        batchJobs.add(batchJob);
        return this;
    }


    public BatchJobsService build() {
        DefaultBatchJobsService defaultBatchJobsService = new DefaultBatchJobsService();
        defaultBatchJobsService.setBatchJobsInvokeStrategy(batchJobsInvokeStrategy);
        defaultBatchJobsService.setBatchJobs(batchJobs);
        if (executor == null) {
            executor = BeanManager.getService("applicationTaskExecutor", ThreadPoolTaskExecutor.class);
        }
        defaultBatchJobsService.setExecutor(executor);
        return defaultBatchJobsService;
    }
}

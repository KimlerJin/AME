package com.ame.util.concurrent;

import com.ame.core.exception.PlatformException;
import com.ame.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultBatchJobsService implements BatchJobsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBatchJobsService.class);
    private List<BatchJob> batchJobs = new ArrayList<>();

    private BatchJobsInvokeStrategy batchJobsInvokeStrategy = BatchJobsInvokeStrategy.COMPLETE_ALL_JOB;

    private Executor executor;

    protected DefaultBatchJobsService() {
        this.batchJobs = batchJobs;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        BatchJob batchJob = new BatchJob() {
            @Override
            public BatchJobResult execute() {
                BatchJobResult batchJobResult = new BatchJobResult();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random random = new Random();
                if (random.nextBoolean()) {
                    throw new PlatformException("111");
                }
                System.err.println(Thread.currentThread());
                batchJobResult.setSuccess(true);
                return batchJobResult;
            }
        };
        BatchJobsServiceBuilder builder = BatchJobsService.builder(executorService);
        builder.job(batchJob);
        builder.job(batchJob);
        builder.job(batchJob);
        builder.job(batchJob);
        builder.job(batchJob);
        builder.job(batchJob);

        BatchJobsService batchJobsService = builder.build();

        BatchJobsFuture invoke = batchJobsService.invoke();
        BatchJobsResult batchJobsResult = invoke.get();
        for (BatchJobResult batchJobResult : batchJobsResult.getBatchJobResults()) {
            System.err.println(batchJobResult.isSuccess());
        }
        executorService.shutdown();
    }

    protected List<BatchJob> getBatchJobs() {
        return batchJobs;
    }

    protected void setBatchJobs(List<BatchJob> batchJobs) {
        this.batchJobs = batchJobs;
    }

    protected void setBatchJobsInvokeStrategy(BatchJobsInvokeStrategy batchJobsInvokeStrategy) {
        this.batchJobsInvokeStrategy = batchJobsInvokeStrategy;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public BatchJobsFuture invoke() {
        CountDownLatch countDownLatch = new CountDownLatch(batchJobs.size());
        BatchJobsResult batchJobsResult = new BatchJobsResult();
        batchJobs.forEach(batchJob -> {
            executor.execute(() -> {
                try {
                    BatchJobResult execute = batchJob.execute();
                    batchJobsResult.addBatchJobResult(execute);
                } catch (Exception e) {
                    LOGGER.error("Job execute failed!", e);
                    BatchJobResult batchJobResult = new BatchJobResult();
                    batchJobResult.setSuccess(false);
                    batchJobResult.setMessage(e.getMessage());
                    PlatformException causeByClass = ExceptionUtils.getCauseByClass(e, PlatformException.class);
                    if (causeByClass == null) {
                        causeByClass = new PlatformException("Job execute failed!");
                    } else {
                        batchJobResult.setMessage(causeByClass.getMessage());
                        batchJobResult.setMessageI18NKey(causeByClass.getErrorCode());
                        batchJobResult.setMessageI18Nparams(causeByClass.getParams());
                    }
                    batchJobResult.setPlatformException(causeByClass);
                    batchJobsResult.addBatchJobResult(batchJobResult);
                } finally {
                    countDownLatch.countDown();
                }
            });
        });


        return new BatchJobsFuture(countDownLatch, batchJobsResult);
    }
}

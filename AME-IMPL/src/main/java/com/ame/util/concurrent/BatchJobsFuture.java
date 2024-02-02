package com.ame.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BatchJobsFuture {

    private CountDownLatch countDownLatch;
    private BatchJobsResult batchJobsResult;

    public BatchJobsFuture(CountDownLatch countDownLatch, BatchJobsResult batchJobsResult) {
        this.countDownLatch = countDownLatch;
        this.batchJobsResult = batchJobsResult;
    }

    public BatchJobsResult get() throws InterruptedException {
        countDownLatch.await();
        return batchJobsResult;
    }

    public BatchJobsResult get(long timeout, TimeUnit unit) throws InterruptedException {
        if (countDownLatch.await(timeout, unit)) {
            return batchJobsResult;
        }
        return null;
    }

    boolean isDone() {
        return countDownLatch.getCount() <= 0;
    }
}

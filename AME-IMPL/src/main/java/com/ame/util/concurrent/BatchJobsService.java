package com.ame.util.concurrent;

import java.util.concurrent.ExecutorService;

public interface BatchJobsService {

    static BatchJobsServiceBuilder builder(ExecutorService executorService) {
        return new BatchJobsServiceBuilder(executorService);
    }

    static BatchJobsServiceBuilder builder() {
        return new BatchJobsServiceBuilder();
    }

    BatchJobsFuture invoke();

}

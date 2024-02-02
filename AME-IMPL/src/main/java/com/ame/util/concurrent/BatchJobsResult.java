package com.ame.util.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BatchJobsResult {

    private List<BatchJobResult> batchJobResults = Collections.synchronizedList(new ArrayList<>());

    public List<BatchJobResult> getBatchJobResults() {
        return batchJobResults;
    }

    public void addBatchJobResult(BatchJobResult batchJobResult) {
        batchJobResults.add(batchJobResult);
    }

    public boolean isAllSuccess() {
        for (BatchJobResult batchJobResult : batchJobResults) {
            if (!batchJobResult.isSuccess()) {
                return false;
            }
        }
        return true;
    }

}

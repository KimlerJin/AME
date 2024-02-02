package com.ame.util.concurrent;

public enum BatchJobsInvokeStrategy {
    /**
     * 所有都执行结束
     */
    COMPLETE_ALL_JOB
//    ,

    /**
     * 其中一个执行失败
     */
//    STOP_OTHER_JOBS_IF_ONE_FAILURE
}

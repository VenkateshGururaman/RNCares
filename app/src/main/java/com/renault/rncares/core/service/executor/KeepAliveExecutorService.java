package com.renault.rncares.core.service.executor;


public class KeepAliveExecutorService extends BaseExecutorService {

    private static final int MAX_THREADS_COUNT = 2 * Runtime.getRuntime().availableProcessors();
    private static final long TASK_TERMINATION_TIMEOUT = 180L;
    private static final String THREAD_TYPE_NAME = "KeepAliveThread";

    public KeepAliveExecutorService() {
        super(MAX_THREADS_COUNT, TASK_TERMINATION_TIMEOUT, THREAD_TYPE_NAME);
    }

}

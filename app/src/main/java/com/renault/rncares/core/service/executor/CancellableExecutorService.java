package com.renault.rncares.core.service.executor;

public class CancellableExecutorService extends BaseExecutorService {

    private static final int MAX_THREADS_COUNT = 2 * Runtime.getRuntime().availableProcessors();
    private static final long TASK_TERMINATION_TIMEOUT = 0L;
    private static final String THREAD_TYPE_NAME = "CancellableThread";

    public CancellableExecutorService() {
        super(MAX_THREADS_COUNT, TASK_TERMINATION_TIMEOUT, THREAD_TYPE_NAME);
    }

}

package com.renault.rncares.core.service.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class BaseExecutorService implements IExecutorService {

    protected int THREAD_KEEP_ALIVE_TIME = 60;
    private final long taskTerminationTimeout;
    private ExecutorService executorService;

    private String EXCEPTION_MESSAGE_DEV_INTERRUPTED_EXCEPTION = "InterruptedException has occurred in BaseExecutorService.";

    /***********************************************************
     * Constructors
     **********************************************************/
    public BaseExecutorService(int maxThreadsCount, long taskTerminationTimeout, String threadName) {
        this.taskTerminationTimeout = taskTerminationTimeout;
        executorService = new ThreadPoolExecutor(0,
                maxThreadsCount,
                THREAD_KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new BaseThreadFactory(threadName));
    }


    @Override
    public void shutdown() {

        shutdownTask();
    }

    private void shutdownTask() {

        if (executorService == null) {
            return;
        }

        // Disable new tasks from being submitted.
        if (taskTerminationTimeout <= 0) {
            executorService.shutdownNow();
        } else {
            executorService.shutdown();
        }

        try {

            // As long as your threads hasn't finished.
            while (!executorService.isTerminated()) {

                // Wait a while for existing tasks to terminate.
                if (!executorService.awaitTermination(taskTerminationTimeout, TimeUnit.SECONDS)) {

                    // Cancel currently executing tasks.
                    executorService.shutdownNow();
                }
            }

        } catch (InterruptedException ie) {
            String devMessage = ie.getMessage();
            if (devMessage == null) {
                devMessage = EXCEPTION_MESSAGE_DEV_INTERRUPTED_EXCEPTION;
            }

            // (Re-)Cancel if current thread also interrupted.
            executorService.shutdownNow();
        }

    }

    @Override
    public void execute(Runnable runnable) {

        executorService.execute(runnable);
    }

    /**
     * Associated thread factory.
     */
    private class BaseThreadFactory implements ThreadFactory {
        private String name;

        public BaseThreadFactory(String name) {
            this.name = name;
        }

        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, name);
        }
    }
}

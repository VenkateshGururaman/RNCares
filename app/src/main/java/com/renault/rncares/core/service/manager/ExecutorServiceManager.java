package com.renault.rncares.core.service.manager;

import com.renault.rncares.core.service.executor.CancellableExecutorService;
import com.renault.rncares.core.service.executor.KeepAliveExecutorService;

public class ExecutorServiceManager {


    public static final int CANCELLABLE = 0;

    public static final int KEEP_ALIVE = 1;

    private CancellableExecutorService cancellableExecutorService = null;
    private KeepAliveExecutorService keepAliveExecutorService = null;


    public ExecutorServiceManager() {

    }

    public synchronized void shutdown() {


        if (cancellableExecutorService != null) {
            cancellableExecutorService.shutdown();
            cancellableExecutorService = null;
        }

        if (keepAliveExecutorService != null) {
            keepAliveExecutorService.shutdown();
            keepAliveExecutorService = null;
        }
    }

    public synchronized void execute(int type, Runnable runnable) {

        switch (type) {
            case KEEP_ALIVE:
                if (keepAliveExecutorService == null) {
                    keepAliveExecutorService = new KeepAliveExecutorService();
                }
                keepAliveExecutorService.execute(runnable);
                break;
            case CANCELLABLE:
            default:
                if (cancellableExecutorService == null) {
                    cancellableExecutorService = new CancellableExecutorService();
                }
                cancellableExecutorService.execute(runnable);
        }
    }

}

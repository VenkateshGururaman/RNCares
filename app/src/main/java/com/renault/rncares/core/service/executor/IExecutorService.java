package com.renault.rncares.core.service.executor;


public interface IExecutorService {

    void execute(Runnable runnable);

    void shutdown();
}

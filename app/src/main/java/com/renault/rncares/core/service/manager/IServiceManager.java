package com.renault.rncares.core.service.manager;

import com.renault.rncares.registration.service.IRegistrationService;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public interface IServiceManager {

    public IRegistrationService getRegistrationService();

    public ExecutorServiceManager getExecutorServiceManager();

}

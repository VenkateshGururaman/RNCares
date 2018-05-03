package com.renault.rncares.core.service.manager;

import com.renault.rncares.registration.service.IRegistrationService;
import com.renault.rncares.registration.service.RegistrationService;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class ServiceManager implements IServiceManager {

    private ExecutorServiceManager mExecutorServiceManager = null;

    private IRegistrationService mRegistrationService = null;

    public ServiceManager() {

    }

    @Override
    public IRegistrationService getRegistrationService() {
        if (mRegistrationService == null) {
            mRegistrationService = new RegistrationService(this);
        }
        return mRegistrationService;
    }

    @Override
    public ExecutorServiceManager getExecutorServiceManager() {
        if (mExecutorServiceManager == null) {
            mExecutorServiceManager = new ExecutorServiceManager();
        }
        return mExecutorServiceManager;
    }

}

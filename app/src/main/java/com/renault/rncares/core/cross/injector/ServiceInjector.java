package com.renault.rncares.core.cross.injector;

import com.renault.rncares.core.BaseApplication;
import com.renault.rncares.core.service.manager.IServiceManager;
import com.renault.rncares.core.service.manager.ServiceManager;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class ServiceInjector {

    public static IServiceManager getServiceManager() {
        if (BaseApplication.getInstance().isServiceManagerExists()) {
            return BaseApplication.getInstance().getServiceManager();
        } else {
            return new ServiceManager();
        }
    }
}

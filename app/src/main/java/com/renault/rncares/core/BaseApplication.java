package com.renault.rncares.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.renault.rncares.core.cross.injector.ServiceInjector;
import com.renault.rncares.core.service.manager.IServiceManager;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class BaseApplication extends Application {

    protected static BaseApplication instance = null;

    protected IServiceManager serviceManager = null;

    public BaseApplication() {
        super();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public SharedPreferences sharedPref(String name) {
        return getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor editorSharedPref(String name) {
        return getInstance().getSharedPreferences(name, Context.MODE_PRIVATE).edit();
    }

    public boolean isServiceManagerExists() {
        return (serviceManager != null);
    }

    public synchronized IServiceManager getServiceManager() {
        if (serviceManager == null) {
            serviceManager = ServiceInjector.getServiceManager();
        }
        return serviceManager;
    }

}

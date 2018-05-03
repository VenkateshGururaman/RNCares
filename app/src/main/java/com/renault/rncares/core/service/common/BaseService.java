package com.renault.rncares.core.service.common;

import android.util.Log;

import com.renault.rncares.core.BaseApplication;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class BaseService {

    protected EventBus eventBus;

    protected BaseApplication application;


    public BaseService() {
        eventBus = EventBus.getDefault();
        application = BaseApplication.getInstance();
        Log.d("BaseService", "Event Bus Instantiated");
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public BaseApplication getApplication() {
        return application;
    }

    public void setApplication(BaseApplication application) {
        this.application = application;
    }

    public void shutdown() {
        if (eventBus != null) {
            eventBus.unregister(this);
            if (application != null) {
                application = null;
            }
        }

        eventBus = null;
    }
}

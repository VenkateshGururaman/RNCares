package com.renault.rncares.core.service.gcm;

import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.renault.rncares.crossfeature.RNCaresApplication;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class RNCaresInstanceIDListenerService extends InstanceIDListenerService {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.d(TAG, "onTokenRefresh");
        RNCaresApplication.getInstance().getServiceManager().getRegistrationService().registerWithGCM();
    }

}

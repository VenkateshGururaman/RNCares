package com.renault.rncares.core.service.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class RNCaresGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle bundle) {
        super.onMessageReceived(from, bundle);
    }
}

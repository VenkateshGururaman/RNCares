package com.renault.rncares.registration.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.renault.rncares.R;
import com.renault.rncares.core.cross.event.BaseEvent;
import com.renault.rncares.core.cross.event.GetDataEvent;
import com.renault.rncares.core.service.common.BaseService;
import com.renault.rncares.core.service.manager.ExecutorServiceManager;
import com.renault.rncares.core.service.manager.IServiceManager;
import com.renault.rncares.crossfeature.RNCaresApplication;
import com.renault.rncares.registration.event.RegistrationEventType;
import com.renault.rncares.registration.model.RegistrationStatus;

import java.io.IOException;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class RegistrationService extends BaseService implements IRegistrationService {

    private static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";

    private final String TAG = this.getClass().getSimpleName();

    private static final String ILLEGAL_ARG_MESSAGE = "ILLEGAL_ARG_MESSAGE";

    private InstanceID instanceID;

    public RegistrationService(IServiceManager serviceManager) {
        super();
        if (serviceManager == null) {
            throw new IllegalArgumentException(ILLEGAL_ARG_MESSAGE);
        }

    }

    @Override
    public void registerWithGCM() {
        RNCaresApplication.getInstance().getServiceManager().getExecutorServiceManager().
                execute(ExecutorServiceManager.KEEP_ALIVE, mRegistrationRunnable);
    }

    private Runnable mRegistrationRunnable = new Runnable() {

        @Override
        public void run() {
            String token = getToken();
            if (!TextUtils.isEmpty(token)) {
                sendRegistrationToServer(token);
                Log.d(TAG, "SentRegistrationData " + token);
            } else {
                Log.d(TAG, "Failed to complete token refresh");
            }
        }

    };

    private String getToken() {
        String token = null;
        instanceID = InstanceID.getInstance(RNCaresApplication.getInstance());
        try {
            token = instanceID.getToken(RNCaresApplication.getInstance().getString(R.string.gcm_projectNumber),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * Persist registration to third-party servers.
     * <p>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        GetDataEvent<RegistrationStatus> event = null;
        RegistrationStatus mRegistration = new RegistrationStatus();
        mRegistration.setStatus(true);
        event = new GetDataEvent<RegistrationStatus>(BaseEvent.STATUS_SUCCESS, null, mRegistration,
                RegistrationEventType.GCM_REGISTRATION);
        eventBus.post(event);
    }

    private void saveTokenInSharedPreferences(boolean isSentToServer) {
        SharedPreferences mSharedPreferences = RNCaresApplication.getInstance().
                getSharedPreferences(SENT_TOKEN_TO_SERVER, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, isSentToServer).apply();
    }

    @Override
    public void unregisterWithGCM() {
        try {
            instanceID.deleteToken(RNCaresApplication.getInstance().getString(R.string.gcm_projectNumber), null);
            saveTokenInSharedPreferences(false);
            Log.d(TAG, "unregisterWithGCM");
        } catch (IOException e) {

        }
    }

}

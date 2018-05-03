package com.renault.rncares.registration.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.renault.rncares.R;
import com.renault.rncares.core.cross.event.GetDataEvent;
import com.renault.rncares.core.view.BaseActivity;
import com.renault.rncares.crossfeature.RNCaresApplication;
import com.renault.rncares.registration.event.RegistrationEventType;
import com.renault.rncares.registration.model.RegistrationStatus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String SERVICE_ACTIVATED = "serviceActivated";

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.switch_service)
    Switch mCheckBoxService;

    @BindView(R.id.button_open_google_maps)
    Button mOpenGoogleMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        registerWithGCM();
    }

    private void initView() {
        mCheckBoxService.setOnCheckedChangeListener(this);
        mOpenGoogleMaps.setOnClickListener(this);
        mCheckBoxService.setChecked(getServiceStateFromPreferences());
    }

    private void registerWithGCM() {
        Log.d(TAG, "registerWithGCM " + getServiceStateFromPreferences());
        if (checkPlayServices() && mCheckBoxService.isChecked()) {
            RNCaresApplication.getInstance().getServiceManager().getRegistrationService().registerWithGCM();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegistrationResponse(GetDataEvent<RegistrationStatus> mEvent) {
        Log.d(TAG, "onRegistrationResponse");
        if (RegistrationEventType.GCM_REGISTRATION.equals(mEvent.getTAG())) {

            if (GetDataEvent.STATUS_SUCCESS.equals(mEvent.getStatus())) {

            } else {

            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        saveServiceStateInSharedPreferences(isChecked);
    }

    private void saveServiceStateInSharedPreferences(boolean isActivated) {
        SharedPreferences mSharedPreferences = RNCaresApplication.getInstance().
                getSharedPreferences(SERVICE_ACTIVATED, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(SERVICE_ACTIVATED, isActivated).apply();
    }

    private boolean getServiceStateFromPreferences() {
        SharedPreferences mSharedPreferences = RNCaresApplication.getInstance().
                getSharedPreferences(SERVICE_ACTIVATED, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(SERVICE_ACTIVATED, true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_open_google_maps) {
            startGoogleMaps();
        }
    }

    private void startGoogleMaps() {
        String uri = "https://www.google.com/maps/search/?api=1&query=hospitals+near+me";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
    }

}

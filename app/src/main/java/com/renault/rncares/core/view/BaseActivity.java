package com.renault.rncares.core.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Venkatesh Gururaman on 06-11-2017
 */

public class BaseActivity extends AppCompatActivity {

    private Unbinder mButterKnifeUnBinder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register to EventBus.
        registerToEventBus();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mButterKnifeUnBinder = ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerToEventBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterFromEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mButterKnifeUnBinder.unbind();
        mButterKnifeUnBinder = null;
    }

    private void registerToEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            Log.d("BaseActivity", "Event Bus Registered");
            EventBus.getDefault().register(this);
        }
    }

    private void unregisterFromEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            Log.d("BaseActivity", "Event Bus Unregistered");
            EventBus.getDefault().unregister(this);
        }
    }

}

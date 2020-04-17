package com.ems.dingdong.app;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.app.realm.DingDongRealm;
import com.ems.dingdong.services.CallService;
import com.ems.dingdong.utiles.Logger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationController extends MultiDexApplication {
    static ApplicationController applicationController;
    private CallService callService;
    private boolean mBound = false;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Logger.d("chauvp", "onServiceConnected");
            CallService.CallBinder binder = (CallService.CallBinder) service;
            callService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());
        Fresco.initialize(this);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .migration(new DingDongRealm()).build();
        Realm.setDefaultConfiguration(config);
        applicationController = this;
        Intent intent = new Intent(this, CallService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public StringeeClient getStringleeClient() {
        return callService.getStringeeClient();
    }

    public void initStringeeClient() {
        if (mBound)
            callService.initStringeeClient();
        else {
            Intent intent = new Intent(this, CallService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    public StringeeCall getmStringeeCall() {
        return callService.getmStringeeCall();
    }

    public void reFreshToken() {
        Logger.d("chauvp", "registerToken");
        callService.registerToken();
    }

    public static ApplicationController getInstance() {
        return applicationController;
    }
}

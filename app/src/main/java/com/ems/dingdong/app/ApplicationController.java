package com.ems.dingdong.app;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.app.realm.DingDongRealm;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Logger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.portsip.PortSipSdk;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationController extends MultiDexApplication {
    private static String TAG = "ApplicationController.class";

    static ApplicationController applicationController;
    private PortSipService portSipService;
    private boolean mBound = false;
    public PortSipSdk portSipSdk;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Logger.d(TAG, "onServiceConnected");
            PortSipService.PortSipBinder binder = (PortSipService.PortSipBinder) service;
            portSipService = binder.getService();
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
        Intent intent = new Intent(this, PortSipService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void initPortSipService() {
        if (mBound) {
            Log.d(TAG, "1" + Thread.currentThread().getName());
            portSipService.registerToServer();
        } else {
            Intent intent = new Intent(this, PortSipService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    public boolean isPortsipConnected() {
        return portSipService.isConnectCallService();
    }

    public static ApplicationController getInstance() {
        return applicationController;
    }
}

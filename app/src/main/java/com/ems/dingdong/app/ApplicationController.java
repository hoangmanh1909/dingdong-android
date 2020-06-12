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
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Logger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.portsip.PortSipSdk;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationController extends MultiDexApplication {
    private static String TAG = "ApplicationController.class";

    static ApplicationController applicationController;
    private CallService callService;
    private PortSipService portSipService;
    private boolean mBound = false;
    public PortSipSdk portSipSdk;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Logger.d(TAG, "onServiceConnected");
//            CallService.CallBinder binder = (CallService.CallBinder) service;
//            callService = binder.getService();
            PortSipService.PortSipBinder binder = (PortSipService.PortSipBinder)service;
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
        portSipSdk = new PortSipSdk();
//        Intent intent = new Intent(this, CallService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        Intent intent = new Intent(this, PortSipService.class);
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

    public void initPortSipService() {
        if (mBound)
            portSipService.registerToServer();
        else {
            Intent intent = new Intent(this, PortSipService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    public StringeeCall getmStringeeCall() {
        return callService.getmStringeeCall();
    }

    public void reFreshToken() {
        Logger.d(TAG, "registerToken");
        callService.registerToken();
    }

    public static ApplicationController getInstance() {
        return applicationController;
    }
}

package com.ems.dingdong.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import com.crashlytics.android.Crashlytics;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.app.realm.DingDongRealm;
import com.ems.dingdong.services.PortSipService;
import com.ems.dingdong.utiles.Logger;
import com.facebook.drawee.backends.pipeline.Fresco;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.utils.RingmeOttSdk;
//import com.ringme.ott.sdk.RingmeOttSdk;
import com.ringme.ott.sdk.RingmeOttSdk;
import com.zoho.commons.LauncherProperties;
import com.zoho.livechat.android.messaging.messenger.api.ZohoMessenger;
import com.zoho.livechat.android.messaging.messenger.api.ZohoService;
import com.zoho.livechat.android.provider.ZohoLDContract;
import com.zoho.salesiqembed.ZohoSalesIQ;


import java.time.ZoneId;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationController extends Application {
    private static String TAG = "ApplicationController.class";

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    static ApplicationController applicationController;
    private PortSipService portSipService;
    private boolean mBound = true;
    //    public PortSipSdk portSipSdk;
    public boolean mConference = false;

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
//        if (!BuildConfig.DEBUG)
//            Fabric.with(this, new Crashlytics());
        Fresco.initialize(this);

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .schemaVersion(0)
//                .deleteRealmIfMigrationNeeded()
//                .migration(new DingDongRealm()).build();
//

//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("DINGDONGOFFLINE.realm")
//                .schemaVersion(1)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(config);
//        Realm.deleteRealm( config );
        RingmeOttSdk.init(getApplicationContext());
        applicationController = this;
        Intent intent = new Intent(this, PortSipService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        ZohoSalesIQ.init(this,
                BuildConfig.APP_KEY,
                BuildConfig.ACCESS_KEY);
//        ZohoSalesIQ.showLauncher(true);
        this.createNotificationChannels();
    }

    public void initPortSipService() {
        if (mBound) {
            Logger.d(TAG, "1" + Thread.currentThread().getName());
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

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "Call ", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is channel call");

            /*NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "Channel 2", NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription("This is channel 2");*/

            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            //manager.createNotificationChannel(channel2);
        }
    }
}

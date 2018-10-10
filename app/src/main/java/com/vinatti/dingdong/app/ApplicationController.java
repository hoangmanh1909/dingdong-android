package com.vinatti.dingdong.app;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.crashlytics.android.Crashlytics;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.app.realm.DingDongRealm;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationController extends MultiDexApplication {
    static ApplicationController applicationController;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());
        Fresco.initialize(this);
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .migration(new DingDongRealm()).build();
        Realm.setDefaultConfiguration(config);
        applicationController = this;
    }

    public static ApplicationController getInstance() {
        return applicationController;
    }
}

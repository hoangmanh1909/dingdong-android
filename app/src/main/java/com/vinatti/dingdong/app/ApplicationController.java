package com.vinatti.dingdong.app;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.crashlytics.android.Crashlytics;
import com.vinatti.dingdong.BuildConfig;

import io.fabric.sdk.android.Fabric;

public class ApplicationController extends MultiDexApplication {
    static ApplicationController applicationController;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());
        Fresco.initialize(this);
        applicationController = this;
    }

    public static ApplicationController getInstance() {
        return applicationController;
    }
}

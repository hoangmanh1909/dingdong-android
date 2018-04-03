package com.vinatti.dingdong.app;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class ApplicationController extends MultiDexApplication {
    static ApplicationController applicationController;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        applicationController = this;
    }

    public static ApplicationController getInstance() {
       return applicationController;
    }
}

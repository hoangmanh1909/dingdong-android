package com.ems.dingdong.functions.mainhome.main.chat;

import android.app.Application;

import com.ems.dingdong.BuildConfig;
import com.zoho.livechat.android.messaging.messenger.api.ZohoChatAPI;
import com.zoho.livechat.android.messaging.messenger.api.ZohoMessenger;
import com.zoho.salesiqembed.ZohoSalesIQ;

public class ChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ZohoSalesIQ.init(this,
                BuildConfig.APP_KEY,
                BuildConfig.ACCESS_KEY);
        ZohoSalesIQ.showLauncher(true);
    }
}

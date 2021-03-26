package com.ems.dingdong.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ems.dingdong.calls.NetWorkReceiver;
import com.portsip.OnPortSIPEvent;

public class PortCtelService extends Service implements  NetWorkReceiver.NetWorkListener {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onNetworkChange(int netMobile) {

    }
}

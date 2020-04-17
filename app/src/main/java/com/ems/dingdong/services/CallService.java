package com.ems.dingdong.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.SharedPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;
import com.stringee.exception.StringeeError;
import com.stringee.listener.StatusListener;
import com.stringee.listener.StringeeConnectionListener;

import org.json.JSONObject;

import java.util.HashMap;

public class CallService extends Service {

    private final IBinder mBinder = new CallBinder();
    private StringeeClient client;
    private StringeeCall mStringeeCall;
    private SharedPref sharedPref;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        sharedPref = SharedPref.getInstance(getApplicationContext());
        initStringeeClient();
        return mBinder;
    }

    public StringeeClient getStringeeClient() {
        initStringeeClient();
        return client;
    }

    public void initStringeeClient() {
        String token = sharedPref.getString(Constants.ACCESS_CALL_TOKEN, "");
        if (client == null) {
            client = new StringeeClient(this);
            client.setConnectionListener(callConnectionListener);
            Logger.d("chauvp", "init");
        }
        if (!TextUtils.isEmpty(token) && !client.isConnected()) {
            client.connect(token);
            Logger.d("chauvp", "connect " + token);
        }

    }

    public StringeeCall getmStringeeCall() {
        return mStringeeCall;
    }

    public class CallBinder extends Binder {
        public CallService getService() {
            return CallService.this;
        }
    }

    public void registerToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (client.isConnected()) {
            client.registerPushToken(token, new StatusListener() {
                @Override
                public void onSuccess() {
                    Logger.d("chauvp", "registerPushToken: onSuccess");
                }

                @Override
                public void onError(StringeeError stringeeError) {
                    super.onError(stringeeError);
                    Logger.d("chauvp", "registerPushToken: onError: " + stringeeError.getMessage());
                }
            });
        } else {
            initStringeeClient();
        }
    }

    private StringeeConnectionListener callConnectionListener = new StringeeConnectionListener() {
        @Override
        public void onConnectionConnected(StringeeClient stringeeClient, boolean b) {
            Logger.d("chauvp", "onConnectionConnected stringee server");
            client = stringeeClient;
            registerToken();
//            Logger.d("chauvp", "onIncomingCall1");
//            HashMap<String, StringeeCall> callHashMap = new HashMap<>();
//            callHashMap.put(Constants.CALL_TYPE, mStringeeCall);
//            Intent intent = new Intent(getApplicationContext(), IncomingCallActivity.class);
//            intent.putExtra(Constants.CALL_TYPE, 0);
//            intent.putExtra(Constants.CALL_MAP, callHashMap);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
        }

        @Override
        public void onConnectionDisconnected(StringeeClient stringeeClient, boolean b) {
            Logger.d("chauvp", "onConnectionDisconnected");
        }

        @Override
        public void onIncomingCall(StringeeCall stringeeCall) {
            Logger.d("chauvp", "onIncomingCall1");
            Logger.d("chauvp", "running on: " + Thread.currentThread().getName());

            new Handler(Looper.getMainLooper()).post(() -> {
                if (stringeeCall != null) {
                    Logger.d("chauvp", "running on: " + Thread.currentThread().getName());
                    Logger.d("chauvp", "start activity: ");
                    HashMap<String, StringeeCall> callHashMap = new HashMap<>();
                    callHashMap.put(Constants.CALL_ID, stringeeCall);
                    mStringeeCall = stringeeCall;
                    mStringeeCall.initAnswer(getApplicationContext(), client);
                    Intent intent = new Intent(getApplicationContext(), IncomingCallActivity.class);
                    intent.putExtra(Constants.CALL_TYPE, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Logger.d("chauvp", "stringeeCall is null");
                }
            });

        }

        @Override
        public void onConnectionError(StringeeClient stringeeClient, StringeeError stringeeError) {
            Logger.d("chauvp", "onConnectionError" + stringeeError.getMessage());
        }

        @Override
        public void onRequestNewToken(StringeeClient stringeeClient) {
            Logger.d("chauvp", "onRequestNewToken");
        }

        @Override
        public void onCustomMessage(String s, JSONObject jsonObject) {
            Logger.d("chauvp", "onCustomMessage");
        }

        @Override
        public void onTopicMessage(String s, JSONObject jsonObject) {
            Logger.d("chauvp", "onTopicMessage");
        }
    };
}

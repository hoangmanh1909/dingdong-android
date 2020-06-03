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

    private static String TAG = "CallService.class";

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
            Logger.d(TAG, "init");
        }
        if (!TextUtils.isEmpty(token) && !client.isConnected()) {
            client.connect(token);
            Logger.d(TAG, "connect " + token);
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
                    Logger.d(TAG, "registerPushToken: onSuccess");
                }

                @Override
                public void onError(StringeeError stringeeError) {
                    super.onError(stringeeError);
                    Logger.d(TAG, "registerPushToken: onError: " + stringeeError.getMessage());
                }
            });
        } else {
            initStringeeClient();
        }
    }

    private StringeeConnectionListener callConnectionListener = new StringeeConnectionListener() {
        @Override
        public void onConnectionConnected(StringeeClient stringeeClient, boolean b) {
            Logger.d(TAG, "onConnectionConnected stringee server");
            client = stringeeClient;
            registerToken();
        }

        @Override
        public void onConnectionDisconnected(StringeeClient stringeeClient, boolean b) {
            Logger.d(TAG, "onConnectionDisconnected");
        }

        @Override
        public void onIncomingCall(StringeeCall stringeeCall) {
            Logger.d(TAG, "onIncomingCall1");
            Logger.d(TAG, "running on: " + Thread.currentThread().getName());

            new Handler(Looper.getMainLooper()).post(() -> {
                if (stringeeCall != null) {
                    Logger.d(TAG, "running on: " + Thread.currentThread().getName());
                    Logger.d(TAG, "start activity: ");
                    HashMap<String, StringeeCall> callHashMap = new HashMap<>();
                    callHashMap.put(Constants.CALL_ID, stringeeCall);
                    mStringeeCall = stringeeCall;
                    mStringeeCall.initAnswer(getApplicationContext(), client);
                    Intent intent = new Intent(getApplicationContext(), IncomingCallActivity.class);
                    intent.putExtra(Constants.CALL_TYPE, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Logger.d(TAG, "stringeeCall is null");
                }
            });

        }

        @Override
        public void onConnectionError(StringeeClient stringeeClient, StringeeError stringeeError) {
            Logger.d(TAG, "onConnectionError" + stringeeError.getMessage());
        }

        @Override
        public void onRequestNewToken(StringeeClient stringeeClient) {
            Logger.d(TAG, "onRequestNewToken");
        }

        @Override
        public void onCustomMessage(String s, JSONObject jsonObject) {
            Logger.d(TAG, "onCustomMessage");
        }

        @Override
        public void onTopicMessage(String s, JSONObject jsonObject) {
            Logger.d(TAG, "onTopicMessage");
        }
    };
}

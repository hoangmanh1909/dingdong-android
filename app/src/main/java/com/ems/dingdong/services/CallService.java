package com.ems.dingdong.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.SharedPref;
import com.stringee.StringeeClient;
import com.stringee.call.StringeeCall;
import com.stringee.exception.StringeeError;
import com.stringee.listener.StringeeConnectionListener;

import org.json.JSONObject;

import java.util.HashMap;

public class CallService extends Service {

    private final IBinder mBinder = new CallBinder();
    private StringeeClient client;
    private StringeeCall stringeeCall;
    private SharedPref sharedPref;
    private boolean isConnected = false;

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
        if (!TextUtils.isEmpty(token) && !isConnected) {
            client.connect(token);
            Logger.d("chauvp", "connect" + token);
        }
    }

    public void makeCall(String fromPhoneNumber, String toPhoneNumber) {
        Logger.d("chauvp", "make call, caller: " + fromPhoneNumber + " callee: " + toPhoneNumber);
        initStringeeClient();
        stringeeCall = new StringeeCall(getApplicationContext(), client, fromPhoneNumber, toPhoneNumber);
        stringeeCall.setCallListener(new StringeeCall.StringeeCallListener() {
            @Override
            public void onSignalingStateChange(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s, int i, String s1) {
                Logger.d("chauvp", "onSignalingStateChange" + signalingState.name());
            }

            @Override
            public void onError(StringeeCall stringeeCall, int i, String s) {
                Logger.d("chauvp", "onError" + s);

            }

            @Override
            public void onHandledOnAnotherDevice(StringeeCall stringeeCall, StringeeCall.SignalingState signalingState, String s) {
                Logger.d("chauvp", "onHandledOnAnotherDevice" + s + signalingState.name());
            }

            @Override
            public void onMediaStateChange(StringeeCall stringeeCall, StringeeCall.MediaState mediaState) {
                Logger.d("chauvp", "onMediaStateChange" + mediaState.name());
            }

            @Override
            public void onLocalStream(StringeeCall stringeeCall) {
                Logger.d("chauvp", "onLocalStream");

            }

            @Override
            public void onRemoteStream(StringeeCall stringeeCall) {
                Logger.d("chauvp", "onRemoteStream");
            }

            @Override
            public void onCallInfo(StringeeCall stringeeCall, JSONObject jsonObject) {
                Logger.d("chauvp", "onCallInfo"+ jsonObject.toString());
            }
        });
        stringeeCall.makeCall();
    }

    public class CallBinder extends Binder {
        public CallService getService() {
            return CallService.this;
        }
    }


    private StringeeConnectionListener callConnectionListener = new StringeeConnectionListener() {
        @Override
        public void onConnectionConnected(StringeeClient stringeeClient, boolean b) {
            Logger.d("chauvp", "onConnectionConnected");
            isConnected = true;
        }

        @Override
        public void onConnectionDisconnected(StringeeClient stringeeClient, boolean b) {
            Logger.d("chauvp", "onConnectionDisconnected");
            isConnected = false;
        }

        @Override
        public void onIncomingCall(StringeeCall stringeeCall) {
            HashMap<String, StringeeCall> callHashMap = new HashMap<>();
            callHashMap.put(stringeeCall.getCallId(), stringeeCall);
            Intent intent = new Intent(getApplicationContext(), IncomingCallActivity.class);
            intent.putExtra(Constants.CALL_ID, stringeeCall.getCallId());
            intent.putExtra(Constants.CALL_TYPE, 0);
            intent.putExtra(Constants.CALL_MAP, callHashMap);
            startActivity(intent);

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

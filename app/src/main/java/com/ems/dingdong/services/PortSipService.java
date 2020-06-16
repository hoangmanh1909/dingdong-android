package com.ems.dingdong.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.utiles.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.portsip.OnPortSIPEvent;
import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipErrorcode;
import com.portsip.PortSipSdk;

import java.util.Random;
import java.util.UUID;

public class PortSipService extends Service implements OnPortSIPEvent {

    private static final String TAG = PortSipService.class.getName();

    public static final String INSTANCE_ID = "instanceid";
    public static final String CALL_EVENT_ANSWER = "CALL_EVENT_ANSWER";
    public static final String CALL_EVENT_RINGING = "CALL_EVENT_RINGING";
    public static final String CALL_EVENT_FAILURE = "CALL_EVENT_FAILURE";
    public static final String CALL_EVENT_INVITE_CONNECTED = "CALL_EVENT_INVITE_CONNECTED";
    public static final String CALL_EVENT_ANSWER_REJECT = "CALL_EVENT_ANSWER_REJECT";
    public static final String CALL_EVENT_ANSWER_ACCEPT = "CALL_EVENT_ANSWER_ACCEPT";
    public static final String CALL_EVENT_END = "CALL_EVENT_END";
    public static final String CALL_EVENT_TRYING = "CALL_EVENT_TRYING";
    public static final String ACTION_CALL_EVENT = "ACTION_CALL_EVENT";
    public static final String TYPE_ACTION = "TYPE_ACTION";
    private final String APPID = "com.tct.dingdong";
    private PortSipSdk mEngine;
    private ApplicationController applicaton;
    private final IBinder mBinder = new PortSipBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        applicaton = (ApplicationController) getApplication();
        mEngine = applicaton.portSipSdk;
        registerToServer();
        return mBinder;
    }

    public void registerToServer() {
        Random rm = new Random();
        int localPort = 5060 + rm.nextInt(60000);
//        int transType = preferences.getInt(TRANS, 0);
//        int srtpType = preferences.getInt(SRTP, 0);
        String userName = "101";
        String password = "123qweXC";
        String displayName = "100";
        String authName = "101";
        String userDomain = "app01.vht.com.vn";

        String sipServer = "portsip.vht.com.vn";
        String serverPort = "5060";
        String stunServer = "";
        String stunPort = "3478";

        int sipServerPort = Integer.parseInt(serverPort);
        int stunServerPort = Integer.parseInt(stunPort);

        int result = 0;
        mEngine.DeleteCallManager();
        mEngine.CreateCallManager(applicaton);
        mEngine.setOnPortSIPEvent(this);
        String dataPath = getExternalFilesDir(null).getAbsolutePath();

        result = mEngine.initialize(PortSipEnumDefine.ENUM_TRANSPORT_UDP, "0.0.0.0", localPort,
                PortSipEnumDefine.ENUM_LOG_LEVEL_DEBUG, dataPath,
                8, "PortSIP SDK for Android", 0, 0, dataPath, "", false, null);
        if (result != PortSipErrorcode.ECoreErrorNone) {
            Log.d(TAG, "initialize failure ErrorCode = " + result);
            mEngine.DeleteCallManager();
            return;
        }

        result = mEngine.setUser(userName, displayName, authName, password,
                userDomain, sipServer, sipServerPort, stunServer, stunServerPort, null, 5060);

        if (result != PortSipErrorcode.ECoreErrorNone) {
            Log.d(TAG, "setUser failure ErrorCode = " + result);
            mEngine.DeleteCallManager();
            return;
        }

        result = mEngine.setLicenseKey("LicenseKey");
        if (result == PortSipErrorcode.ECoreWrongLicenseKey) {
            Log.d(TAG, "The wrong license key was detected, please check with sales@portsip.com or support@portsip.com");
            return;
        } else if (result == PortSipErrorcode.ECoreTrialVersionLicenseKey) {
            Log.w("Trial Version", "This trial version SDK just allows short conversation, you can't hearing anything after 2-3 minutes, contact us: sales@portsip.com to buy official version.");
            Log.d(TAG, "This Is Trial Version");
        }

        mEngine.setAudioDevice(PortSipEnumDefine.AudioDevice.SPEAKER_PHONE);
        mEngine.setVideoDeviceId(1);
        mEngine.setSrtpPolicy(0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ConfigPreferences(this, preferences, mEngine);

        mEngine.enable3GppTags(false);
        String token = FirebaseInstanceId.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            String pushMessage = "device-os=android;device-uid=" + token + ";allow-call-push=true;allow-message-push=true;app-id=" + APPID;
            mEngine.addSipMessageHeader(-1, "REGISTER", 1, "portsip-push", pushMessage);
            //new version
            mEngine.addSipMessageHeader(-1, "REGISTER", 1, "x-p-push", pushMessage);
        }

        mEngine.setInstanceId(getInstanceID());

        result = mEngine.registerServer(90, 0);
        if (result != PortSipErrorcode.ECoreErrorNone) {
            Log.d(TAG, "registerServer failure ErrorCode =" + result);
            mEngine.unRegisterServer();
            mEngine.DeleteCallManager();
        }
    }

    private String getInstanceID() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String insanceid = preferences.getString(INSTANCE_ID, "");
        if (TextUtils.isEmpty(insanceid)) {
            insanceid = UUID.randomUUID().toString();
            preferences.edit().putString(INSTANCE_ID, insanceid).commit();
        }
        return insanceid;
    }

    @Override
    public void onRegisterSuccess(String s, int i, String s1) {
        Log.d(TAG, "onRegisterSuccess!");
    }

    @Override
    public void onRegisterFailure(String s, int i, String s1) {
        Log.d(TAG, "onRegisterFailure!");
    }

    @Override
    public void onInviteIncoming(long l, String s, String s1, String s2, String s3, String s4, String s5, boolean b, boolean b1, String s6) {
        Log.d(TAG, "onInviteIncoming!" + s + s1 + s2 + s3);
        CallManager.Instance().getSession().phoneNumber = s;
        CallManager.Instance().getSession().displayName = s2;
        CallManager.Instance().getSession().sessionID = l;
        Log.d(TAG, "Sessionid: " + l);
        Intent intent = new Intent(getApplicationContext(), IncomingCallActivity.class);
        intent.putExtra(Constants.CALL_TYPE, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Ring.getInstance(getApplication()).startRingTone();
    }

    @Override
    public void onInviteTrying(long l) {
        Log.d(TAG, "onInviteTrying!");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_TRYING);
        sendBroadcast(intent);
    }

    @Override
    public void onInviteSessionProgress(long l, String s, String s1, boolean b, boolean b1, boolean b2, String s2) {
        Log.d(TAG, "onInviteSessionProgress!");
    }

    @Override
    public void onInviteRinging(long l, String s, int i, String s1) {
        Log.d(TAG, "onInviteRinging!");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_RINGING);
        sendBroadcast(intent);
    }

    @Override
    public void onInviteAnswered(long l, String s, String s1, String s2, String s3, String s4, String s5, boolean b, boolean b1, String s6) {
        Log.d(TAG, "onInviteAnswered!");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_ANSWER);
        sendBroadcast(intent);
    }

    @Override
    public void onInviteFailure(long l, String s, int i, String s1) {
        Log.d(TAG, "onInviteFailure!" + "s: " + s + " l: " + l + " i: " + i + " s1: " + s1);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_FAILURE);
        sendBroadcast(intent);
    }

    @Override
    public void onInviteUpdated(long l, String s, String s1, boolean b, boolean b1, String s2) {
        Log.d(TAG, "onRegisterFailure!");
    }

    @Override
    public void onInviteConnected(long l) {
        Log.d(TAG, "onInviteConnected!");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_INVITE_CONNECTED);
        sendBroadcast(intent);
    }

    @Override
    public void onInviteBeginingForward(String s) {
        Log.d(TAG, "onInviteBeginingForward!");
    }

    @Override
    public void onInviteClosed(long l) {
        Log.d(TAG, "onInviteClosed!");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_END);
        sendBroadcast(intent);
    }

    @Override
    public void onDialogStateUpdated(String s, String s1, String s2, String s3) {
        Log.d(TAG, "onDialogStateUpdated!");
    }

    @Override
    public void onRemoteHold(long l) {
        Log.d(TAG, "onRemoteHold!");
    }

    @Override
    public void onRemoteUnHold(long l, String s, String s1, boolean b, boolean b1) {
        Log.d(TAG, "onRemoteUnHold!");
    }

    @Override
    public void onReceivedRefer(long l, long l1, String s, String s1, String s2) {
        Log.d(TAG, "onReceivedRefer: ");
    }

    @Override
    public void onReferAccepted(long l) {
        Log.d(TAG, "onReferAccepted: ");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_ANSWER_ACCEPT);
        sendBroadcast(intent);
    }

    @Override
    public void onReferRejected(long l, String s, int i) {
        Log.d(TAG, "onReferRejected!");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_ANSWER_REJECT);
        sendBroadcast(intent);
    }

    @Override
    public void onTransferTrying(long l) {
        Log.d(TAG, "onTransferTrying!");
    }

    @Override
    public void onTransferRinging(long l) {
        Log.d(TAG, "onTransferRinging!");
    }

    @Override
    public void onACTVTransferSuccess(long l) {
        Log.d(TAG, "onACTVTransferSuccess!");
    }

    @Override
    public void onACTVTransferFailure(long l, String s, int i) {
        Log.d(TAG, "onACTVTransferFailure!");
    }

    @Override
    public void onReceivedSignaling(long l, String s) {
        Log.d(TAG, "onReceivedSignaling!");
    }

    @Override
    public void onSendingSignaling(long l, String s) {
        Log.d(TAG, "onSendingSignaling!");
    }

    @Override
    public void onWaitingVoiceMessage(String s, int i, int i1, int i2, int i3) {
        Log.d(TAG, "onRegisterFailure!");
    }

    @Override
    public void onWaitingFaxMessage(String s, int i, int i1, int i2, int i3) {
        Log.d(TAG, "onWaitingFaxMessage!");
    }

    @Override
    public void onRecvDtmfTone(long l, int i) {
        Log.d(TAG, "onRecvDtmfTone!");
    }

    @Override
    public void onRecvOptions(String s) {
        Log.d(TAG, "onRecvOptions!");
    }

    @Override
    public void onRecvInfo(String s) {
        Log.d(TAG, "onRecvInfo: ");
    }

    @Override
    public void onRecvNotifyOfSubscription(long l, String s, byte[] bytes, int i) {
        Log.d(TAG, "onRecvNotifyOfSubscription: ");
    }

    @Override
    public void onPresenceRecvSubscribe(long l, String s, String s1, String s2) {
        Log.d(TAG, "onPresenceRecvSubscribe: ");
    }

    @Override
    public void onPresenceOnline(String s, String s1, String s2) {
        Log.d(TAG, "onPresenceOnline: ");
    }

    @Override
    public void onPresenceOffline(String s, String s1) {
        Log.d(TAG, "onPresenceOffline: ");
    }

    @Override
    public void onRecvMessage(long l, String s, String s1, byte[] bytes, int i) {
        Log.d(TAG, "onRecvMessage: ");
    }

    @Override
    public void onRecvOutOfDialogMessage(String s, String s1, String s2, String s3, String s4, String s5, byte[] bytes, int i, String s6) {
        Log.d(TAG, "onRecvOutOfDialogMessage: ");
    }

    @Override
    public void onSendMessageSuccess(long l, long l1) {
        Log.d(TAG, "onSendMessageSuccess: ");
    }

    @Override
    public void onSendMessageFailure(long l, long l1, String s, int i) {
        Log.d(TAG, "onSendMessageFailure: ");
    }

    @Override
    public void onSendOutOfDialogMessageSuccess(long l, String s, String s1, String s2, String s3) {
        Log.d(TAG, "onSendOutOfDialogMessageSuccess!");
    }

    @Override
    public void onSendOutOfDialogMessageFailure(long l, String s, String s1, String s2, String s3, String s4, int i) {
        Log.d(TAG, "onSendOutOfDialogMessageFailure!");
    }

    @Override
    public void onSubscriptionFailure(long l, int i) {
        Log.d(TAG, "onSubscriptionFailure!");
    }

    @Override
    public void onSubscriptionTerminated(long l) {
        Log.d(TAG, "onSubscriptionTerminated!");
    }

    @Override
    public void onPlayAudioFileFinished(long l, String s) {
        Log.d(TAG, "onPlayAudioFileFinished!");
    }

    @Override
    public void onPlayVideoFileFinished(long l) {
        Log.d(TAG, "onPlayVideoFileFinished!");
    }

    @Override
    public void onReceivedRTPPacket(long l, boolean b, byte[] bytes, int i) {
        Log.d(TAG, "onReceivedRTPPacket!");
    }

    @Override
    public void onSendingRTPPacket(long l, boolean b, byte[] bytes, int i) {
        Log.d(TAG, "onSendingRTPPacket!");
    }

    @Override
    public void onAudioRawCallback(long l, int i, byte[] bytes, int i1, int i2) {
        Log.d(TAG, "onAudioRawCallback!");
    }

    @Override
    public void onVideoRawCallback(long l, int i, int i1, int i2, byte[] bytes, int i3) {
        Log.d(TAG, "onVideoRawCallback!");
    }

    public class PortSipBinder extends Binder {
        public PortSipService getService() {
            return PortSipService.this;
        }
    }

    public static void ConfigPreferences(Context context, SharedPreferences preferences, PortSipSdk sdk) {
        sdk.clearAudioCodec();
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_G722);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_PCMA);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_PCMU);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_G729);

        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_GSM);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_ILBC);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_AMR);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_AMRWB);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_SPEEX);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_SPEEXWB);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_ISACWB);
        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_ISACSWB);

        sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_OPUS);

        sdk.clearVideoCodec();
        sdk.addVideoCodec(PortSipEnumDefine.ENUM_VIDEOCODEC_H264);
        sdk.addVideoCodec(PortSipEnumDefine.ENUM_VIDEOCODEC_VP8);
        sdk.addVideoCodec(PortSipEnumDefine.ENUM_VIDEOCODEC_VP9);
        sdk.enableAEC(true);
        sdk.enableAGC(true);
        sdk.enableCNG(true);
        sdk.enableVAD(true);
        sdk.enableANS(false);

        boolean foward = true;
        boolean fowardBusy = true;
        String fowardto = preferences.getString(context.getString(R.string.batch_code), null);
        if (foward && !TextUtils.isEmpty(fowardto)) {
            sdk.enableCallForward(fowardBusy, fowardto);
        }

        sdk.enableReliableProvisional(false);

        int width = 352;
        int height = 288;
        sdk.setVideoResolution(width, height);
    }
}

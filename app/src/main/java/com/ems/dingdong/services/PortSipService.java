package com.ems.dingdong.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.calls.answer.AnswerActivity;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.portsip.OnPortSIPEvent;
import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipErrorcode;
import com.portsip.PortSipSdk;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PortSipService extends Service implements OnPortSIPEvent {

    private static final String TAG = PortSipService.class.getName();

    public static final String ACTION_SIP_REGIEST = "PortSip.AndroidSample.Test.REGIEST";

    public static final String INSTANCE_ID = "instanceid";
    public static final String CALL_EVENT_ANSWER = "CALL_EVENT_ANSWER";
    public static final String CALL_EVENT_INVITE_COMMING = "CALL_EVENT_INVITE_COMMING";
    public static final String CALL_EVENT_RINGING = "CALL_EVENT_RINGING";
    public static final String CALL_EVENT_FAILURE = "CALL_EVENT_FAILURE";
    public static final String CALL_EVENT_INVITE_CONNECTED = "CALL_EVENT_INVITE_CONNECTED";
    public static final String CALL_EVENT_ANSWER_REJECT = "CALL_EVENT_ANSWER_REJECT";
    public static final String CALL_EVENT_ANSWER_ACCEPT = "CALL_EVENT_ANSWER_ACCEPT";
    public static final String CALL_EVENT_END = "CALL_EVENT_END";
    public static final String CALL_EVENT_TRYING = "CALL_EVENT_TRYING";
    public static final String ACTION_CALL_EVENT = "ACTION_CALL_EVENT";
    public static final String ACTION_LOG_OUT = "ACTION_LOG_OUT";
    public static final String TYPE_ACTION = "TYPE_ACTION";
    private final String APPID = "com.tct.dingdong";

    ///
    public static final String ACTION_PUSH_MESSAGE = "PortSip.AndroidSample.Test.PushMessageIncoming";
    public static final String ACTION_PUSH_TOKEN = "PortSip.AndroidSample.Test.PushToken";
    public static final String ACTION_SIP_UNREGIEST = "PortSip.AndroidSample.Test.UNREGIEST";
    public static final String ACTION_SIP_REINIT = "PortSip.AndroidSample.Test.TrnsType";
    public static final String EXTRA_PUSHTOKEN = "token";
    private String pushToken;
    private String channelID = "PortSipService";
    private NotificationManager mNotificationManager;
    public static final String EXTRA_CALL_SEESIONID = "SessionID";
    public static final String EXTRA_PHONE_CALLER = "Caller";
    private static final int SERVICE_NOTIFICATION  = 31414;
    public  static final int PENDINGCALL_NOTIFICATION= SERVICE_NOTIFICATION+1;
    public static final String CALL_CHANGE_ACTION = "PortSip.AndroidSample.Test.CallStatusChagnge";
    public static final String EXTRA_CALL_DESCRIPTION = "Description";
    private String callChannelID = "Call Channel";
    public static final String REGISTER_CHANGE_ACTION = "PortSip.AndroidSample.Test.RegisterStatusChagnge";
    public static final String PRESENCE_CHANGE_ACTION = "PortSip.AndroidSample.Test.PRESENCEStatusChagnge";




    private SharedPreferences preferences;


    private PortSipSdk mEngine;
    private ApplicationController applicaton;
    private final IBinder mBinder = new PortSipBinder();
    private boolean isConnectCallService = false;
    private Disposable disposable;
    private BroadcastReceiver logoutEvent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_LOG_OUT.equals(intent.getAction())) {
                if (disposable != null) {
                    disposable.dispose();
                    disposable = null;
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void registerToServer() {
        applicaton.portSipSdk = new PortSipSdk();
        mEngine = applicaton.portSipSdk;
        Random rm = new Random();
        Log.d(TAG, Thread.currentThread().getName());
        int localPort = 5060 + rm.nextInt(60000);
//        int transType = preferences.getInt(TRANS, 0);
//        int srtpType = preferences.getInt(SRTP, 0);


/*        String userName = "1000001969803622";
        //String userName = "1000001963170164";
        String password = "Abcd1234";
        String displayName = "1000001969803622";
        //String displayName = "1000001963170164";
        String authName = "";
        String userDomain = "vnpost-shipper.vht.com.vn";
        String sipServer = "sip.vht.com.vn";
        String serverPort = "5060";
        String stunServer = "";
        String stunPort = "3478";

        int sipServerPort = Integer.parseInt(serverPort);
        int stunServerPort = Integer.parseInt(stunPort);*/


        mEngine.DeleteCallManager();
        mEngine.CreateCallManager(applicaton);
        mEngine.setOnPortSIPEvent(this);
        String dataPath = getExternalFilesDir(null).getAbsolutePath();
        Log.d(TAG, "before init");
        disposable = Observable.fromCallable(() -> {
                    Log.d(TAG, Thread.currentThread().getName());
                    return mEngine.initialize(PortSipEnumDefine.ENUM_TRANSPORT_UDP, "0.0.0.0", localPort,
                            PortSipEnumDefine.ENUM_LOG_LEVEL_DEBUG, dataPath,
                            8, "PortSIP SDK for Android", 0,
                            0, dataPath, "", false, null);
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    int result = integer;
                    Log.d(TAG, Thread.currentThread().getName());
                    if (result != PortSipErrorcode.ECoreErrorNone) {
                        Log.d(TAG, "initialize failure ErrorCode = " + result);
                        mEngine.DeleteCallManager();
                        return;
                    }

                    SharedPref sharedPref = new SharedPref(this);
                    String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                    UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);

                    String userName = userInfo.getExtensionUserName();
                    String password = userInfo.getExtensionPassword();
                    String displayName = userInfo.getExtensionUserName();
                    String authName = userInfo.getExtensionStunServer();
                    String userDomain = userInfo.getExtensionDomain();
                    String sipServer = userInfo.getExtensionServer();
                    String serverPort = userInfo.getExtensionServerPort();
                    String stunServer = userInfo.getExtensionStunServer();
                    String stunPort = userInfo.getExtensionStunServerPort();
                    int sipServerPort = Integer.parseInt(serverPort);
                    int stunServerPort = Integer.parseInt(stunPort);


                    Log.d(TAG, "before set user");
                    result = mEngine.setUser(userName, displayName, authName, password, userDomain, sipServer, sipServerPort, stunServer, stunServerPort, null, 5060);

                    if (result != PortSipErrorcode.ECoreErrorNone) {
                        Log.d(TAG, "setUser failure ErrorCode = " + result);
                        mEngine.DeleteCallManager();
                        return;
                    }
                    Log.d(TAG, "before This Is Trial Version");
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
//            mEngine.addSipMessageHeader(-1, "REGISTER", 1, "portsip-push", pushMessage);
                        //new version
                        mEngine.addSipMessageHeader(-1, "REGISTER", 1, "x-p-push", pushMessage);
                    }

                    mEngine.setInstanceId(getInstanceID());
                    result = mEngine.registerServer(90, 0);
                    if (result != PortSipErrorcode.ECoreErrorNone) {
                        Log.d(TAG, "registerServer failure ErrorCode =" + result);
                        mEngine.unRegisterServer();
                        mEngine.DeleteCallManager();
                        isConnectCallService = false;
                    } else {
                        isConnectCallService = true;
                    }
                }, throwable -> Log.d(TAG, throwable.getMessage()));
    }

    /*public void userInfo(final Object model){
        UserInfo item = (UserInfo) model;
        String userName = item.getExtensionUserName();
        Log.d("12121", "b: "+ userName);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        applicaton = (ApplicationController) getApplication();
        registerLogoutEvent();
    }

    private void registerLogoutEvent() {
        applicaton.registerReceiver(logoutEvent, new IntentFilter(ACTION_LOG_OUT));
    }

    private String getInstanceID() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String insanceid = preferences.getString(INSTANCE_ID, "");
        if (TextUtils.isEmpty(insanceid)) {
            insanceid = UUID.randomUUID().toString();
            preferences.edit().putString(INSTANCE_ID, insanceid).commit();
        }
        return insanceid;
    }

    @Override
    public void onDestroy() {
        if (logoutEvent != null) {
            applicaton.unregisterReceiver(logoutEvent);
        }
        super.onDestroy();
    }

    public boolean isConnectCallService() {
        return isConnectCallService;
    }

    ///


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            if (ACTION_PUSH_MESSAGE.equals(intent.getAction())||ACTION_SIP_REGIEST.equals(intent.getAction())){
                CallManager.Instance().online = true;
                if(CallManager.Instance().regist){
                    mEngine.refreshRegistration(0);
                }else {
                    registerToServer();
                }
            } else if (ACTION_SIP_UNREGIEST.equals(intent.getAction())) {
                CallManager.Instance().online = false;
                unregisterToServer();
            }else if (ACTION_SIP_REINIT.equals(intent.getAction())) {
                CallManager.Instance().hangupAllCalls(mEngine);
                //initialSDK();
            }else if (ACTION_PUSH_TOKEN.equals(intent.getAction())) {
                pushToken = intent.getStringExtra(EXTRA_PUSHTOKEN);

                refreshPushToken();
            }

        }
        return result;
    }

    ///
    public void unregisterToServer() {

        mEngine.unRegisterServer();
        CallManager.Instance().regist = false;
    }

    private void refreshPushToken(){
        if (!TextUtils.isEmpty(pushToken))
        {
            String pushMessage = "device-os=android;device-uid=" + pushToken + ";allow-call-push=true;allow-message-push=true;app-id=" + APPID;
            //old version
            mEngine.addSipMessageHeader(-1, "REGISTER", 1, "portsip-push", pushMessage);
            //new version
            mEngine.addSipMessageHeader(-1, "REGISTER", 1, "x-p-push", pushMessage);

            mEngine.refreshRegistration(0);

        }
    }

    @Override
    public void onRegisterSuccess(String s, int i, String s1) {
        Log.d(TAG, "onRegisterSuccess!");
        Log.d("123123", "onRegisterSuccess");
    }

    @Override
    public void onRegisterFailure(String s, int i, String s1) {
        Log.d(TAG, "onRegisterFailure!");
        Log.d("123123", "onRegisterFailure");
    }

    //Incomming Call
    @Override
    public void onInviteIncoming(long sessionId, String callerDisplayName, String caller, String calleeDisplayName, String callee, String audioCodecNames, String videoCodecNames, boolean existsAudio, boolean existsVideo, String sipMessage) {
        //Log.d(TAG, "onInviteIncoming!" + callerDisplayName + caller + calleeDisplayName + callee);
        /*CallManager.Instance().getSession().phoneNumber = callerDisplayName;
        CallManager.Instance().getSession().displayName = calleeDisplayName;
        CallManager.Instance().getSession().sessionID = sessionId;*/

        /*try {
            CallManager.Instance().getSession().phoneNumber = callerDisplayName;
            CallManager.Instance().getSession().displayName = calleeDisplayName;
            CallManager.Instance().getSession().sessionID = sessionId;
        }catch (NullPointerException nullPointerException){

        }
        //Log.d(TAG, "Sessionid: " + sessionId);
        Log.d("1231234", "onInviteIncoming: "+ sessionId + " callerDisplayName: "+ callerDisplayName);

        if (CallManager.Instance().findIncomingCall() != null){
            applicaton.portSipSdk.rejectCall(sessionId, 486);//busy
            return;
        }
        Session session = CallManager.Instance().findIdleSession();
        session.state = Session.CALL_STATE_FLAG.INCOMING;
        session.hasVideo = existsVideo;
        session.sessionID = sessionId;
        session.remote = caller;
        session.displayName = callerDisplayName;

        if (Utils.isIncomingCallRunning(getBaseContext())) {
            Intent intent = new Intent(ACTION_CALL_EVENT);
            intent.putExtra(TYPE_ACTION, CALL_EVENT_INVITE_COMMING);
            sendBroadcast(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);
            intent.putExtra(Constants.CALL_TYPE, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        Ring.getInstance(getApplication()).startRingTone();*/



        if(CallManager.Instance().findIncomingCall()!=null){
            applicaton.portSipSdk.rejectCall(sessionId,486);//busy
            return;
        }
        Session session = CallManager.Instance().findIdleSession();
        session.state = Session.CALL_STATE_FLAG.INCOMING;
        session.hasVideo = existsVideo;
        session.sessionID = sessionId;
        session.remote = caller;
        session.displayName = callerDisplayName;

        Intent activityIntent = new Intent(this, AnswerActivity.class);
        ///Intent activityIntent = new Intent(this, IncomingCallActivity.class);
        activityIntent.putExtra(EXTRA_CALL_SEESIONID,sessionId);
        activityIntent.putExtra(EXTRA_PHONE_CALLER, caller);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if(isForeground()){
            startActivity(activityIntent);
        }else{
            try {
                showPendingCallNotification(this, callerDisplayName, caller, activityIntent);
            }catch (NullPointerException nullPointerException){};
            //showPendingCallNotification(this, callerDisplayName, caller, activityIntent);
        }
        Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
        String description = session.lineName + " onInviteIncoming";

        broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
        broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

        sendPortSipMessage(description, broadIntent);


        Ring.getInstance(this).startRingTone();

    }

    private boolean isForeground(){
        String[] activitys;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            activitys = getActivePackages(this);
        }else{
            activitys = getActivePackagesCompat(this);
        }
        if(activitys.length>0){
            String packagename = getPackageName();
            //String processName= getProcessName();||activityname.contains(processName)
            for(String activityname:activitys){

                if(activityname.contains(packagename)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private String[] getActivePackagesCompat(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    private String[] getActivePackages(Context context) {
        final Set<String> activePackages = new HashSet<String>();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }

    public void showPendingCallNotification(Context context, String contenTitle,String contenText,Intent intent) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, callChannelID)
                .setSmallIcon(R.drawable.mapbox_logo_icon)
                .setContentTitle(contenTitle)
                .setContentText(contenText)
                .setAutoCancel(true)
                .setShowWhen(true)
                .setContentIntent(contentIntent)
                .setFullScreenIntent(contentIntent, true);
        try {
            mNotificationManager.notify(PENDINGCALL_NOTIFICATION, builder.build());
        }catch (NullPointerException nullPointerException){};
        //mNotificationManager.notify(PENDINGCALL_NOTIFICATION, builder.build());
    }

    public void sendPortSipMessage(String message, Intent broadIntent) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this,channelID);
        }else{
            builder = new Notification.Builder(this);
        }

        try {
            builder.setSmallIcon(R.drawable.mapbox_logo_icon)
                    .setContentTitle("Sip Notify")
                    .setContentText(message)
                    .setContentIntent(contentIntent)
                    .build();// getNotification()

            mNotificationManager.notify(1, builder.build());

            sendBroadcast(broadIntent);
        }catch (NullPointerException nullPointerException){

        }
    }

    @Override
    public void onInviteTrying(long l) {
        Log.d(TAG, "onInviteTrying!");
        Log.d("123123", " onInviteTrying: "+" sessionID: " + l);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_TRYING);
        sendBroadcast(intent);
    }

    @Override
    public void onInviteSessionProgress(long sessionId, String audioCodecNames, String videoCodecNames, boolean existsEarlyMedia, boolean existsAudio, boolean existsVideo, String sipMessage) {
        Log.d(TAG, "onInviteSessionProgress!");
        Log.d("123123", " onInviteSessionProgress: "+" sessionId: "+ sessionId + " audioCodecNames: "+audioCodecNames);

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null){
            session.bEarlyMedia = existsEarlyMedia;
        }

    }

    @Override
    public void onInviteRinging(long sessionId, String statusText, int statusCode, String sipMessage) {
        /*Log.d(TAG, "onInviteRinging!");
        Log.d("123123", " onInviteRinging: "+" sessionId: "+ sessionId+" statusText: "+" statusCode: "+ statusCode+" sipMessage: "+sipMessage);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_RINGING);
        sendBroadcast(intent);*/

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null && !session.bEarlyMedia) {
            Ring.getInstance(this).startRingBackTone();
        }
        Log.d("123123", "onInviteRinging " + "sessionId: "+ sessionId);
    }

    @Override
    public void onInviteAnswered(long sessionId, String callerDisplayName, String caller, String calleeDisplayName, String callee, String audioCodecNames, String videoCodecNames, boolean existsAudio, boolean existsVideo, String sipMessage) {
        /*Log.d(TAG, "onInviteAnswered!");
        Log.d("123123", " onInviteAnswered: "+ " sessionId: "+sessionId+" callerDisplayName: "+callerDisplayName+" caller: "+caller+" calleeDisplayName: "+calleeDisplayName+" callee: "+callee+" audioCodecNames: "+audioCodecNames+" videoCodecNames: "+videoCodecNames+" existsAudio: "+existsAudio+" existsVideo: "+" sipMessage: "+sipMessage);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_ANSWER);
        sendBroadcast(intent);

        ///
        Ring.getInstance(this).stopRingBackTone();*/

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CONNECTED;
            session.hasVideo = existsVideo;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);

            String description = session.lineName + " onInviteAnswered";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
        }

        Ring.getInstance(this).stopRingBackTone();
        Log.d("123123", "onInviteRinging " + "sessionId: "+ sessionId);
    }

    @Override
    public void onInviteFailure(long sessionId, String s, int i, String s1) {
        /*Log.d(TAG, "onInviteFailure!" + "s: " + s + " l: " + l + " i: " + i + " s1: " + s1);
        Log.d("123123", "onInviteFailure");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_FAILURE);
        sendBroadcast(intent);*/

        ///
        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.FAILED;
            session.sessionID = sessionId;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " onInviteFailure";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
        }

        Ring.getInstance(this).stopRingBackTone();
        Log.d("123123", "onInviteFailure " + "sessionId: "+ sessionId);
    }

    @Override
    public void onInviteUpdated(long sessionId, String s, String s1, boolean b, boolean existsVideo, String s2) {
        Log.d(TAG, "onRegisterFailure!");

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CONNECTED;
            session.hasVideo = existsVideo;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " OnInviteUpdated";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
        }
    }

    @Override
    public void onInviteConnected(long sessionId) {
        /*Log.d(TAG, "onInviteConnected!");
        Log.d("123123", " onInviteConnected: "+" sessionId: "+sessionId);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_INVITE_CONNECTED);
        sendBroadcast(intent);

        //
        if(applicaton.portSipSdk.getAudioDevices().contains(PortSipEnumDefine.AudioDevice.BLUETOOTH)){
            applicaton.portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.BLUETOOTH);
        }else {
            CallManager.Instance().setSpeakerOn(applicaton.portSipSdk, CallManager.Instance().isSpeakerOn());
        }*/

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CONNECTED;
            session.sessionID = sessionId;

            if (applicaton.mConference)
            {
                applicaton.portSipSdk.joinToConference(session.sessionID);
                applicaton.portSipSdk.sendVideo(session.sessionID, true);
            }

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " OnInviteConnected";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
        }
        if(applicaton.portSipSdk.getAudioDevices().contains(PortSipEnumDefine.AudioDevice.BLUETOOTH)){
            applicaton.portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.BLUETOOTH);
        }else {
            CallManager.Instance().setSpeakerOn(applicaton.portSipSdk, CallManager.Instance().isSpeakerOn());
        }
        Log.d("123123", "onInviteConnected " + "sessionId: "+ sessionId);
    }

    @Override
    public void onInviteBeginingForward(String s) {
        Log.d(TAG, "onInviteBeginingForward!");
    }

    @Override
    public void onInviteClosed(long sessionId) {
        /*Log.d(TAG, "onInviteClosed!");
        Log.d("123123", " onInviteClosed: "+" sessionId: "+sessionId);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_END);
        sendBroadcast(intent);

        Ring.getInstance(this).stopRingTone();*/

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CLOSED;
            session.sessionID = sessionId;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " OnInviteClosed";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
        }
        Ring.getInstance(this).stopRingTone();
        Log.d("123123", "onInviteClosed " + "sessionId: "+ sessionId);
    }

    @Override
    public void onDialogStateUpdated(String s, String s1, String s2, String s3) {
        Log.d(TAG, "onDialogStateUpdated!");
    }

    @Override
    public void onRemoteHold(long l) {
        Log.d(TAG, "onRemoteHold!");
        Log.d("123123", "onRemoteHold!");
    }

    @Override
    public void onRemoteUnHold(long l, String s, String s1, boolean b, boolean b1) {
        Log.d(TAG, "onRemoteUnHold!");
        Log.d("123123", "onRemoteUnHold!");
    }

    @Override
    public void onReceivedRefer(long l, long l1, String s, String s1, String s2) {
        Log.d(TAG, "onReceivedRefer: ");
    }

    @Override
    public void onReferAccepted(long sessionId) {
        /*Log.d(TAG, "onReferAccepted: ");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_ANSWER_ACCEPT);
        sendBroadcast(intent);*/

        ///
        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CLOSED;
            session.sessionID = sessionId;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " onReferAccepted";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
        }
        Ring.getInstance(this).stopRingTone();
        Log.d("123123", "onReferAccepted " + "sessionId: "+ sessionId);
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
    public void onACTVTransferSuccess(long sessionId) {
        Log.d(TAG, "onACTVTransferSuccess!");
        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CLOSED;
            session.sessionID = sessionId;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " Transfer succeeded, call closed";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
            // Close the call after succeeded transfer the call
            mEngine.hangUp(sessionId);
        }
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
        if (preferences.getBoolean(context.getString(R.string.MEDIA_PCMA), true)) {
            sdk.addAudioCodec(PortSipEnumDefine.ENUM_AUDIOCODEC_PCMA);
        }
    }


}

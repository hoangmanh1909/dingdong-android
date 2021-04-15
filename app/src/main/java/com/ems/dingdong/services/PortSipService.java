package com.ems.dingdong.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.R;
import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.calls.CallManager;
import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.calls.NetWorkReceiver;
import com.ems.dingdong.calls.Ring;
import com.ems.dingdong.calls.Session;
import com.ems.dingdong.calls.calling.CallingFragment;
import com.ems.dingdong.functions.mainhome.main.MainActivity;
import com.ems.dingdong.functions.mainhome.profile.CustomCallerInAndSessonIdIn;
import com.ems.dingdong.functions.mainhome.profile.CustomItem;
import com.ems.dingdong.model.AccountCtel;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.portsip.OnPortSIPEvent;
import com.portsip.PortSipEnumDefine;
import com.portsip.PortSipErrorcode;
import com.portsip.PortSipSdk;
import com.sip.cmc.SipCmc;

import org.greenrobot.eventbus.EventBus;
import org.linphone.core.LinphoneCall;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.sip.cmc.callback.PhoneCallback;
import com.sip.cmc.callback.RegistrationCallback;
import com.sip.cmc.network.Account;

public class PortSipService extends Service implements OnPortSIPEvent, NetWorkReceiver.NetWorkListener {

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
    public static final String CALL_EVENT_END_CTEL = "CALL_EVENT_END_CTEL";
    public static final String CALL_EVENT_TRYING = "CALL_EVENT_TRYING";
    public static final String CALL_EVENT_SESSION_PROGRESS = "CALL_EVENT_SESSION_PROGRESS";
    public static final String ACTION_CALL_EVENT = "ACTION_CALL_EVENT";
    public static final String ACTION_LOG_OUT = "ACTION_LOG_OUT";
    public static final String TYPE_ACTION = "TYPE_ACTION";
    private final String APPID = "com.tct.dingdong";

    public static final String ACTION_PUSH_MESSAGE = "PortSip.AndroidSample.Test.PushMessageIncoming";
    public static final String ACTION_PUSH_TOKEN = "PortSip.AndroidSample.Test.PushToken";
    public static final String ACTION_SIP_UNREGIEST = "PortSip.AndroidSample.Test.UNREGIEST";
    public static final String ACTION_SIP_REINIT = "PortSip.AndroidSample.Test.TrnsType";
    public static final String EXTRA_PUSHTOKEN = "token";
    private String pushToken;
    private String channelID = "PortSipService";
    private NotificationManager mNotificationManager;
    public static final String EXTRA_CALL_SEESIONID = "SessionID";
    public static final String SIP_MESSAGE = "SIP_MESSAGE";
    public static final String EXTRA_PHONE_CALLER = "Caller";
    private static final int SERVICE_NOTIFICATION = 31414;
    public static final int PENDINGCALL_NOTIFICATION = SERVICE_NOTIFICATION + 1;
    public static final String CALL_CHANGE_ACTION = "PortSip.AndroidSample.Test.CallStatusChagnge";
    public static final String EXTRA_CALL_DESCRIPTION = "Description";
    private String callChannelID = "Call Channel";
    public static final String REGISTER_CHANGE_ACTION = "PortSip.AndroidSample.Test.RegisterStatusChagnge";
    public static final String PRESENCE_CHANGE_ACTION = "PortSip.AndroidSample.Test.PRESENCEStatusChagnge";
    public static final String EXTRA_REGISTER_STATE = "RegisterStatus";
    protected PowerManager.WakeLock mCpuLock;
    private NetWorkReceiver mNetWorkReceiver;
    String callerNumberCustomer = "";
    CallingFragment callingFragment;

    private String CHANNEL_ID = "VoipChannel";
    private String CHANNEL_NAME = "Voip Channel";

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
        eventLoginAndCallCtel();

        applicaton.portSipSdk = new PortSipSdk();
        mEngine = applicaton.portSipSdk;

        Random rm = new Random();
        Log.d(TAG, Thread.currentThread().getName());
        int localPort = 5060 + rm.nextInt(60000);
        mEngine.DeleteCallManager();
        mEngine.CreateCallManager(applicaton);
        mEngine.setOnPortSIPEvent(this);
        String dataPath = getExternalFilesDir(null).getAbsolutePath();
        Log.d(TAG, "before init");
//        Log.d("khiempt",SipCmc.getAccountInfo().getName());

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

                    String poProvinceCode = userInfo.getPOProvinceCode();
                    String poDistricCode = userInfo.getPODistrictCode();
//                    Log.d("123123", "userInfo.getExtensionUserName(): "+userInfo.getExtensionUserName() +" getiD(): "+userInfo.getiD() + " poProvinceCode :"
//                            +poProvinceCode +" poDistricCode : " + poDistricCode + " authName : " + authName);


                    Log.d(TAG, "before set user");
                    result = mEngine.setUser(userName, displayName, authName, password, userDomain, sipServer, sipServerPort, stunServer, stunServerPort, null, 5060);

                    Log.d("123123", "userInfo.getExtensionUserName(): " + userInfo.getExtensionUserName() + " getiD(): " + userInfo.getiD() + " poProvinceCode :"
                            + poProvinceCode + " poDistricCode : " + poDistricCode + " result : " + result);

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

//                    Log.d("khiempt", "This Is Trial Version" + result);
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

    @Override
    public void onCreate() {
        super.onCreate();
        applicaton = (ApplicationController) getApplication();
        registerLogoutEvent();

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            NotificationChannel callChannel = new NotificationChannel(callChannelID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mNotificationManager.createNotificationChannel(callChannel);
        }

        registerReceiver();
        //Tạm thời comment
        showServiceNotification();//notification call app to app

    }

    private void showServiceNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0/*requestCode*/, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, channelID);
        } else {
            builder = new Notification.Builder(this);
        }
        builder.setSmallIcon(R.drawable.ic_logo_ding_dong)
                ///.setContentTitle(getString(R.string.app_name))
                .setContentText("Service Running")
                .setContentIntent(contentIntent)
                .build();// getNotification()
        startForeground(SERVICE_NOTIFICATION, builder.build());
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetWorkReceiver = new NetWorkReceiver();
        //mNetWorkReceiver.setListener((NetWorkReceiver.NetWorkListener) this);

        registerReceiver(mNetWorkReceiver, filter);
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
        /*if (logoutEvent != null) {
            applicaton.unregisterReceiver(logoutEvent);
        }*/

        SipCmc.stopService(this);

        mEngine.destroyConference();
        unregisterReceiver();
        if (mCpuLock != null) {
            mCpuLock.release();
        }
        mNotificationManager.cancelAll();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mNotificationManager.deleteNotificationChannel(channelID);
            mNotificationManager.deleteNotificationChannel(callChannelID);
        }
        mNotificationManager = null;

        mEngine.removeUser();
        mEngine.DeleteCallManager();

        super.onDestroy();
    }

    public boolean isConnectCallService() {
        return isConnectCallService;
    }

    private void unregisterReceiver() {
        if (mNetWorkReceiver != null) {
            unregisterReceiver(mNetWorkReceiver);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            if (ACTION_PUSH_MESSAGE.equals(intent.getAction()) || ACTION_SIP_REGIEST.equals(intent.getAction())) {
                CallManager.Instance().online = true;
                if (CallManager.Instance().regist) {
                    mEngine.refreshRegistration(0);
                } else {
                    registerToServer();
                }
            } else if (ACTION_SIP_UNREGIEST.equals(intent.getAction())) {
                CallManager.Instance().online = false;
                unregisterToServer();
            } else if (ACTION_SIP_REINIT.equals(intent.getAction())) {
                CallManager.Instance().hangupAllCalls(mEngine);
                //initialSDK();
            } else if (ACTION_PUSH_TOKEN.equals(intent.getAction())) {
                pushToken = intent.getStringExtra(EXTRA_PUSHTOKEN);

                refreshPushToken();
            }
        }


        return result;
    }

    public void unregisterToServer() {

        mEngine.unRegisterServer();
        CallManager.Instance().regist = false;
    }

    private void refreshPushToken() {
        if (!TextUtils.isEmpty(pushToken)) {
            String pushMessage = "device-os=android;device-uid=" + pushToken + ";allow-call-push=true;allow-message-push=true;app-id=" + APPID;

            try {
                //old version
                mEngine.addSipMessageHeader(-1, "REGISTER", 1, "portsip-push", pushMessage);

                //new version
                mEngine.addSipMessageHeader(-1, "REGISTER", 1, "x-p-push", pushMessage);

                mEngine.refreshRegistration(0);
            } catch (NullPointerException nullPointerException) {
            }
        }
    }

    @Override
    public void onRegisterSuccess(String statusText, int statusCode, String sipMessage) {
        Log.d(TAG, "onRegisterSuccess!");
        Log.d("123123123123", "onRegisterSuccess" + statusText);

        CallManager.Instance().regist = true;
        Intent broadIntent = new Intent(REGISTER_CHANGE_ACTION);
        broadIntent.putExtra(EXTRA_REGISTER_STATE, statusText);
        ///sendPortSipMessage("onRegisterSuccess", broadIntent);

        keepCpuRun(true);
    }

    public void keepCpuRun(boolean keepRun) {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (keepRun == true) { //open
            if (mCpuLock == null) {
                if ((mCpuLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SipSample:CpuLock.")) == null) {
                    return;
                }
                mCpuLock.setReferenceCounted(false);
            }

            synchronized (mCpuLock) {
                if (!mCpuLock.isHeld()) {
                    mCpuLock.acquire();
                }
            }
        } else {//close
            if (mCpuLock != null) {
                synchronized (mCpuLock) {
                    if (mCpuLock.isHeld()) {
                        mCpuLock.release();
                    }
                }
            }
        }
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

        ///
        /*Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_INVITE_COMMING);
        sendBroadcast(intent);*/


        if (CallManager.Instance().findIncomingCall() != null) {
            applicaton.portSipSdk.rejectCall(sessionId, 486);//busy
            return;
        }
        Session session = CallManager.Instance().findIdleSession();
        session.state = Session.CALL_STATE_FLAG.INCOMING;
        session.hasVideo = existsVideo;
        session.sessionID = sessionId;
        session.remote = caller;
        session.displayName = callerDisplayName;

        Intent activityIntent = new Intent(this, IncomingCallActivity.class);
        activityIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        callerNumberCustomer = sipMessage.substring(168, 178);
        String xSessionIdCustomerIn = mEngine.getSipMessageHeaderValue(sipMessage, "X-Session-Id");
        Log.d("123123", "xSessionIdCustomerIn:" + xSessionIdCustomerIn);
        EventBus.getDefault().postSticky(new CustomCallerInAndSessonIdIn(callerNumberCustomer, xSessionIdCustomerIn));

        if (isForeground()) {
            startActivity(activityIntent);
        } else {
            showPendingCallNotification(this, callerDisplayName, caller, activityIntent);
        }
        Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
        ///String description = session.lineName + " onInviteIncoming";
        String description = "Cuộc gọi đến: " + session.displayName;

        broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
        broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

        ///sendPortSipMessage(description, broadIntent);

        /*String str = sipMessage;
        str = str.replaceAll("[^0-9{17,18},-\\.]", ",");
        String[] item = str.split(",");

// duyet cac phan tu, neu la so thi in ra
        for (int i = 0; i < item.length; i++) {
            try {
                Double.parseDouble(item[i]);
                System.out.println(item[i]);
                Log.d("123123", "onInviteIncoming " + "number:"+ item[i]);
            } catch (NumberFormatException e) {
            }
        }*/

        //Toast.makeText(applicaton, ""+item, Toast.LENGTH_LONG).show();
        Ring.getInstance(this).startRingTone();
        Log.d("123123", "onInviteIncoming " + "sipMessage:" + sipMessage);
        Log.d("123123", "onInviteIncoming " + "callerNumberCustomer:" + callerNumberCustomer);
        Log.d("123123", "onInviteIncoming " + "xSessionIdIn:" + xSessionIdCustomerIn.trim());

    }

    private boolean isForeground() {
        String[] activitys;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            activitys = getActivePackages(this);
        } else {
            activitys = getActivePackagesCompat(this);
        }
        if (activitys.length > 0) {
            String packagename = getPackageName();
            //String processName= getProcessName();||activityname.contains(processName)
            for (String activityname : activitys) {

                if (activityname.contains(packagename)) {
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

    public void showPendingCallNotification(Context context, String contenTitle, String contentText, Intent intent) {
        ///old
        /*PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, callChannelID)
                .setSmallIcon(R.drawable.ic_logo_ding_dong)
                .setContentTitle(contenTitle)
                .setContentText(contenText)
                .setAutoCancel(true)
                .setShowWhen(true)
                .setContentIntent(contentIntent)
                .setFullScreenIntent(contentIntent, true);
        mNotificationManager.notify(PENDINGCALL_NOTIFICATION, builder.build());*/


        ///new
        createChannel();

        Intent yesReceive = new Intent();
        yesReceive.setAction(Constants.YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent yesReceive2 = new Intent();
        yesReceive2.setAction(Constants.STOP_ACTION);
        PendingIntent pendingIntentNo = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_ding_dong)
                .setContentTitle(contenTitle)
                .setContentText(contentText)
                .setAutoCancel(true)
                /*.setShowWhen(true)*/
                .addAction(R.drawable.ic_call_hang_green, "Nghe", pendingIntentYes)
                .addAction(R.drawable.ic_call_end_red, "Tắt máy", pendingIntentNo)
                .setContentIntent(contentIntent)
                .setFullScreenIntent(contentIntent, true);
        mNotificationManager.notify(PENDINGCALL_NOTIFICATION, builder.build());

    }

    /*
    Create noticiation channel if OS version is greater than or eqaul to Oreo
    */
    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Call Notifications");
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE),
                    new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setLegacyStreamType(AudioManager.STREAM_RING)
                            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION).build());
            Objects.requireNonNull(getApplicationContext().getSystemService(NotificationManager.class)).createNotificationChannel(channel);
        }
    }

    public void sendPortSipMessage(String message, Intent broadIntent) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, channelID);
        } else {
            builder = new Notification.Builder(this);
        }

        try {
            builder.setSmallIcon(R.drawable.ic_logo_ding_dong)
                    .setContentTitle("Thông báo mới")//Sip Notify
                    .setContentText(message)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build();// getNotification()

            mNotificationManager.notify(1, builder.build());

            sendBroadcast(broadIntent);
        } catch (NullPointerException nullPointerException) {
        }
    }

    @Override
    public void onInviteTrying(long sessionId) {
        Log.d(TAG, "onInviteTrying!");
        Log.d("123123", " onInviteTrying: " + " sessionID: " + sessionId);

        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_TRYING);
        intent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
        sendBroadcast(intent);
    }

    @Override
    public void onInviteSessionProgress(long sessionId, String audioCodecNames, String videoCodecNames, boolean existsEarlyMedia, boolean existsAudio, boolean existsVideo, String sipMessage) {
        Log.d(TAG, "onInviteSessionProgress!");
        Log.d("123123", " onInviteSessionProgress: " + " sipMessage " + sipMessage);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_SESSION_PROGRESS);
        sendBroadcast(intent);


        //String xSessionIdPostmanOut = sipMessage.substring(446, 464);
        String xSessionIdPostmanOut = mEngine.getSipMessageHeaderValue(sipMessage, "X-Session-Id");

        Log.d("123123", " xSessionIdPostmanOut:" + xSessionIdPostmanOut);
        //Toast.makeText(applicaton, ""+xSessionId, Toast.LENGTH_LONG).show();


        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.INVITESESSION_PROGRESS;
            session.bEarlyMedia = existsEarlyMedia;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sipMessage);
            //String description = session.lineName + " OnInviteClosed";
            String description = "Cuộc gọi nhỡ: " + callerNumberCustomer;
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);

            EventBus.getDefault().postSticky(new CustomItem(xSessionIdPostmanOut));

            //get header
            /*Header[] headers = response.getAllHeaders();
            private HashMap<String, String> convertHeadersToHashMap(Header[] headers) {
                HashMap<String, String> result = new HashMap<String, String>(headers.length);
                for (Header header : headers) {
                    result.put(header.getName(), header.getValue());
                }
                return result;
            }*/

        }
    }

    @Override
    public void onInviteRinging(long sessionId, String statusText, int statusCode, String sipMessage) {
        /*Log.d(TAG, "onInviteRinging!");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_RINGING);
        sendBroadcast(intent);

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null && !session.bEarlyMedia) {
            Ring.getInstance(this).startRingBackTone();
        }*/
        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null && !session.bEarlyMedia) {
            Ring.getInstance(this).startRingBackTone();
        }

        Log.d("123123", " onInviteRinging: " + " sessionId: " + sessionId + " statusText: " + statusText + " statusCode: " + statusCode + " sipMessage: " + sipMessage);
    }

    @Override
    public void onInviteAnswered(long sessionId, String callerDisplayName, String caller, String calleeDisplayName, String callee, String audioCodecNames, String videoCodecNames, boolean existsAudio, boolean existsVideo, String sipMessage) {
        Log.d(TAG, "onInviteAnswered!");
        Log.d("123123", " onInviteAnswered: " + " sessionId: " + sessionId + " callerDisplayName: " + callerDisplayName + " caller: " + caller + " calleeDisplayName: " + calleeDisplayName + " callee: " + callee + " audioCodecNames: " + audioCodecNames + " videoCodecNames: " + videoCodecNames + " existsAudio: " + existsAudio + " existsVideo: " + " sipMessage: " + sipMessage);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_ANSWER);
        sendBroadcast(intent);

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CONNECTED;
            session.hasVideo = existsVideo;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);

            String description = session.lineName + " onInviteAnswered";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            ///sendPortSipMessage(description, broadIntent);
        }

        Ring.getInstance(this).stopRingBackTone();
        Log.d("123123", "onInviteRinging " + "sessionId: " + sessionId);
    }

    @Override
    public void onInviteFailure(long sessionId, String reason, int code, String sipMessage) {
        Log.d("123123", "onInviteFailure");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_FAILURE);
        sendBroadcast(intent);


        Session session = CallManager.Instance().findSessionBySessionID(sessionId);

        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.FAILED;
            session.sessionID = sessionId;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            //String description = session.lineName + " onInviteFailure";
            /*String description = "Cuộc gọi nhỡ: "+ session.displayName;
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);*/
        }

        Ring.getInstance(this).stopRingBackTone();
        Log.d("123123", "onInviteFailure " + "sessionId: " + sessionId + " reason " + reason + " code " + code + " sipMessage " + sipMessage);
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

            ///sendPortSipMessage(description, broadIntent);
        }
    }

    @Override
    public void onInviteConnected(long sessionId) {
        Log.d(TAG, "onInviteConnected!");
        Log.d("123123", " onInviteConnected: " + " sessionId: " + sessionId);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_INVITE_CONNECTED);
        sendBroadcast(intent);

        //
        if (applicaton.portSipSdk.getAudioDevices().contains(PortSipEnumDefine.AudioDevice.BLUETOOTH)) {
            applicaton.portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.BLUETOOTH);
        } else {
            CallManager.Instance().setSpeakerOn(applicaton.portSipSdk, CallManager.Instance().isSpeakerOn());
        }

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CONNECTED;
            session.sessionID = sessionId;

            if (applicaton.mConference) {
                applicaton.portSipSdk.joinToConference(session.sessionID);
                applicaton.portSipSdk.sendVideo(session.sessionID, true);
            }

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " OnInviteConnected";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            ///sendPortSipMessage(description, broadIntent);
        }
        if (applicaton.portSipSdk.getAudioDevices().contains(PortSipEnumDefine.AudioDevice.BLUETOOTH)) {
            applicaton.portSipSdk.setAudioDevice(PortSipEnumDefine.AudioDevice.BLUETOOTH);
        } else {
            CallManager.Instance().setSpeakerOn(applicaton.portSipSdk, CallManager.Instance().isSpeakerOn());
        }
        Log.d("123123", "onInviteConnected " + "sessionId: " + sessionId);
    }

    @Override
    public void onInviteBeginingForward(String s) {
        Log.d(TAG, "onInviteBeginingForward!");
    }

    @Override
    public void onInviteClosed(long sessionId) {
        Log.d(TAG, "onInviteClosed!");
        Log.d("123123", " onInviteClosed: " + " sessionId: " + sessionId);
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_END);
        sendBroadcast(intent);

        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CLOSED;
            session.sessionID = sessionId;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            //String description = session.lineName + " OnInviteClosed";
            String description = "Cuộc gọi nhỡ: " + callerNumberCustomer;
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            sendPortSipMessage(description, broadIntent);
        }
        Ring.getInstance(this).stopRingTone();
        mNotificationManager.cancel(PENDINGCALL_NOTIFICATION);

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
        Log.d(TAG, "onReferAccepted: ");
        Intent intent = new Intent(ACTION_CALL_EVENT);
        intent.putExtra(TYPE_ACTION, CALL_EVENT_ANSWER_ACCEPT);
        sendBroadcast(intent);

        ///
        Session session = CallManager.Instance().findSessionBySessionID(sessionId);
        if (session != null) {
            session.state = Session.CALL_STATE_FLAG.CLOSED;
            session.sessionID = sessionId;

            Intent broadIntent = new Intent(CALL_CHANGE_ACTION);
            broadIntent.putExtra(EXTRA_CALL_SEESIONID, sessionId);
            String description = session.lineName + " onReferAccepted";
            broadIntent.putExtra(EXTRA_CALL_DESCRIPTION, description);

            ///sendPortSipMessage(description, broadIntent);
        }
        Ring.getInstance(this).stopRingTone();
        Log.d("123123", "onReferAccepted " + "sessionId: " + sessionId);
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

            ///sendPortSipMessage(description, broadIntent);
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

    @Override
    public void onNetworkChange(int netMobile) {
        if (netMobile == -1) {
            //invaluable
        } else {
            if (CallManager.Instance().online) {
                applicaton.portSipSdk.refreshRegistration(0);
            } else {
                //
            }
        }
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

    private void eventLoginAndCallCtel() {
        SharedPref sharedPref = new SharedPref(this);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        ///
        SipCmc.startService(this);
        SipCmc.loginAccount(userInfo.getUserName(), BuildConfig.DOMAIN_CTEL,BuildConfig.AUTH_CTEL);
        SipCmc.addCallback(new RegistrationCallback() {
            @Override
            public void registrationOk() {
                super.registrationOk();
                Log.d("registrationOk", "login ctel registrationOk");
            }

            @Override
            public void registrationFailed() {
                super.registrationFailed();
                Log.d("registrationFailed", "login ctel failed");
            }

            @Override
            public void registrationNone() {
                super.registrationNone();
                Log.d("registrationNone", "login ctel registrationNone");
            }

            @Override
            public void registrationProgress() {
                super.registrationProgress();
                Log.d("registrationProgress", "login ctel registrationProgress");
            }
            @Override
            public void registrationCleared() {
                super.registrationCleared();
                Log.d("123123", "login ctel registrationCleared");
            }
        }, null);

        SipCmc.addCallback(null, new PhoneCallback() {
            @Override
            public void incomingCall(LinphoneCall linphoneCall) {
                super.incomingCall(linphoneCall);
                Log.d("123123", "incomingCall: ");
                Session session = CallManager.Instance().findIdleSession();
                session.state = Session.CALL_STATE_FLAG.INCOMING;
                Intent activityIntent = new Intent(getApplicationContext(), IncomingCallActivity.class);
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (isForeground()) {
                    startActivity(activityIntent);
                } else {
                    showPendingCallNotification(getApplicationContext(), "Cuộc gọi Ctel", linphoneCall.toString(), activityIntent);
                }

            }

            @Override
            public void outgoingInit() {
                super.outgoingInit();
                Log.d("123123", "outgoingInit");
            }

            @Override
            public void callConnected(LinphoneCall linphoneCall) {
                super.callConnected(linphoneCall);
                Log.d("123123", "callConnected");
                Intent intent = new Intent(ACTION_CALL_EVENT);
                intent.putExtra(TYPE_ACTION, CALL_EVENT_INVITE_CONNECTED);
                sendBroadcast(intent);
                mNotificationManager.cancel(PENDINGCALL_NOTIFICATION);
            }

            @Override
            public void callEnd(LinphoneCall linphoneCall) {
                super.callEnd(linphoneCall);
                Log.d("123123", "callEnd");
                //Toast.makeText(getApplicationContext(), "callEnd", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(ACTION_CALL_EVENT);
                    intent.putExtra(TYPE_ACTION, CALL_EVENT_END);
                    sendBroadcast(intent);
                } catch (NullPointerException nullPointerException) {
                }

                mNotificationManager.cancel(PENDINGCALL_NOTIFICATION);

            }

            @Override
            public void callReleased() {
                super.callReleased();
                Log.d("123123", "callReleased");
                //Toast.makeText(getApplicationContext(), "callReleased", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(ACTION_CALL_EVENT);
                    intent.putExtra(TYPE_ACTION, CALL_EVENT_END);
                    sendBroadcast(intent);
                } catch (NullPointerException nullPointerException) {
                }
            }

            @Override
            public void error() {
                super.error();
                Log.d("123123", "error");
            }

            @Override
            public void callStatus(int status) {
                super.callStatus(status);
                Log.d("123123", "callStatus: " + status);
            }

            @Override
            public void callTimeRing(String time) {
                super.callTimeRing(time);
                Log.d("123123", "callTimeRing");
            }

            @Override
            public void callTimeAnswer(String time) {
                super.callTimeAnswer(time);
                Log.d("123123", "callTimeAnswer");
            }

            @Override
            public void callTimeEnd(String time) {
                super.callTimeEnd(time);
                Log.d("123123", "callTimeEnd");
            }

            @Override
            public void callId(String callId) {
                super.callId(callId);
                Log.d("123123", "callId");
            }

            @Override
            public void callPhoneNumber(String phoneNumber) {
                super.callPhoneNumber(phoneNumber);
                Log.d("123123", "callPhoneNumber: " + phoneNumber);
            }

            @Override
            public void callDuration(long duration) {
                super.callDuration(duration);
                Log.d("123123", "callDuration: " + duration);
            }
        });
        // Login SipCmc
//
    }

}

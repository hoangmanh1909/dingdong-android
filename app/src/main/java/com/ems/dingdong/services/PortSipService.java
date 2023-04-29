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
//import com.sip.cmc.SipCmc;

import org.greenrobot.eventbus.EventBus;

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

//import com.sip.cmc.callback.PhoneCallback;
//import com.sip.cmc.callback.RegistrationCallback;
//import com.sip.cmc.network.Account;

public class PortSipService extends Service implements NetWorkReceiver.NetWorkListener {

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
    private String callChannelID = "Call Channel";
    private NotificationManager mNotificationManager;
    public static final String EXTRA_CALL_SEESIONID = "SessionID";
    public static final String SIP_MESSAGE = "SIP_MESSAGE";
    public static final String EXTRA_PHONE_CALLER = "Caller";
    private static final int SERVICE_NOTIFICATION = 31414;
    public static final int PENDINGCALL_NOTIFICATION = SERVICE_NOTIFICATION + 1;
    public static final String CALL_CHANGE_ACTION = "PortSip.AndroidSample.Test.CallStatusChagnge";
    public static final String EXTRA_CALL_DESCRIPTION = "Description";

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
    //    private PortSipSdk mEngine;
    private ApplicationController applicaton;
    private boolean isConnectCallService = false;
    private final IBinder mBinder = new PortSipBinder();
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

    public class PortSipBinder extends Binder {
        public PortSipService getService() {
            return PortSipService.this;
        }
    }

    public void registerToServer() {
        eventLoginAndCallCtel();
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
//        showServiceNotification();//notification call app to app

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
                .setContentText("Service Running")
                .setContentIntent(contentIntent)
                .build();// getNotification()
        startForeground(SERVICE_NOTIFICATION, builder.build());
//        registerToServer();
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
//        SipCmc.stopService(this);
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

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                registerToServer();
            } else if (ACTION_SIP_UNREGIEST.equals(intent.getAction())) {
                CallManager.Instance().online = false;
                unregisterToServer();
            } else if (ACTION_SIP_REINIT.equals(intent.getAction())) {
//                CallManager.Instance().hangupAllCalls(mEngine);
                //initialSDK();
            } else if (ACTION_PUSH_TOKEN.equals(intent.getAction())) {
                pushToken = intent.getStringExtra(EXTRA_PUSHTOKEN);

                refreshPushToken();
            }
        }


        return result;
    }

    public void unregisterToServer() {
        CallManager.Instance().regist = false;
    }

    private void refreshPushToken() {
        if (!TextUtils.isEmpty(pushToken)) {
            String pushMessage = "device-os=android;device-uid=" + pushToken + ";allow-call-push=true;allow-message-push=true;app-id=" + APPID;
            try {

            } catch (NullPointerException nullPointerException) {
            }
        }
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

    private void eventLoginAndCallCtel() {
        SharedPref sharedPref = new SharedPref(this);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//        SipCmc.startService(this);
//        SipCmc.addCallback(new RegistrationCallback() {
//            @Override
//            public void registrationOk() {
//                super.registrationOk();
//                Log.d("registrationOk", "login ctel registrationOk");
//            }
//
//            @Override
//            public void registrationFailed() {
//                super.registrationFailed();
//                Log.d("registrationFailed", "login ctel failed");
//            }
//
//            @Override
//            public void registrationNone() {
//                super.registrationNone();
//                Log.d("registrationNone", "login ctel registrationNone");
//            }
//
//            @Override
//            public void registrationProgress() {
//                super.registrationProgress();
//                Log.d("registrationProgress", "login ctel registrationProgress");
//            }
//
//            @Override
//            public void registrationCleared() {
//                super.registrationCleared();
//                Log.d("123123", "login ctel registrationCleared");
//            }
//        }, null);
//
//        SipCmc.addCallback(null, new PhoneCallback() {
//            @Override
//            public void incomingCall(LinphoneCall linphoneCall) {
//                super.incomingCall(linphoneCall);
//                Log.d("123123khiem", "incomingCall 1234: ");
//                Intent activityIntent = new Intent(getApplicationContext(), IncomingCallActivity.class);
//                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (isForeground()) {
//                startActivity(activityIntent);
//                } else {
//                    showPendingCallNotification(getApplicationContext(), "Cuộc gọi Ctel", linphoneCall.toString(), activityIntent);
//                }
//
//            }
//
//            @Override
//            public void outgoingInit() {
//                super.outgoingInit();
//                Log.d("123123khiem", "outgoingInit");
//            }
//
//            @Override
//            public void callConnected(LinphoneCall linphoneCall) {
//                super.callConnected(linphoneCall);
//                Intent intent = new Intent(ACTION_CALL_EVENT);
//                intent.putExtra(TYPE_ACTION, CALL_EVENT_INVITE_CONNECTED);
//                sendBroadcast(intent);
//                mNotificationManager.cancel(PENDINGCALL_NOTIFICATION);
//            }
//
//            @Override
//            public void callEnd(LinphoneCall linphoneCall) {
//                super.callEnd(linphoneCall);
//                Log.d("123123khiem", "callEnd");
//                //Toast.makeText(getApplicationContext(), "callEnd", Toast.LENGTH_SHORT).show();
//                try {
//                    Intent intent = new Intent(ACTION_CALL_EVENT);
//                    intent.putExtra(TYPE_ACTION, CALL_EVENT_END);
//                    sendBroadcast(intent);
//                } catch (NullPointerException nullPointerException) {
//                }
//
//                mNotificationManager.cancel(PENDINGCALL_NOTIFICATION);
//
//            }
//
//            @Override
//            public void callReleased() {
//                super.callReleased();
//                Log.d("123123khiem", "callReleased");
//                //Toast.makeText(getApplicationContext(), "callReleased", Toast.LENGTH_SHORT).show();
//                try {
//                    Intent intent = new Intent(ACTION_CALL_EVENT);
//                    intent.putExtra(TYPE_ACTION, CALL_EVENT_END);
//                    sendBroadcast(intent);
//                } catch (NullPointerException nullPointerException) {
//                }
//            }
//
//            @Override
//            public void error() {
//                super.error();
//                Log.d("123123khiem", "error");
//            }
//
//            @Override
//            public void callStatus(int status) {
//                super.callStatus(status);
//                Log.d("123123khiem", "callStatus: " + status);
//            }
//
//            @Override
//            public void callTimeRing(String time) {
//                super.callTimeRing(time);
//                Log.d("123123khiem", "callTimeRing");
//            }
//
//            @Override
//            public void callTimeAnswer(String time) {
//                super.callTimeAnswer(time);
//                Log.d("123123khiem", "callTimeAnswer");
//            }
//
//            @Override
//            public void callTimeEnd(String time) {
//                super.callTimeEnd(time);
//                Log.d("123123khiem", "callTimeEnd");
//            }
//
//            @Override
//            public void callId(String callId) {
//                super.callId(callId);
//                Log.d("123123khiem", "callId");
//            }
//
//            @Override
//            public void callPhoneNumber(String phoneNumber) {
//                super.callPhoneNumber(phoneNumber);
//                Log.d("123123khiem", "callPhoneNumber: " + phoneNumber);
//            }
//
//            @Override
//            public void callDuration(long duration) {
//                super.callDuration(duration);
//                Log.d("123123khiem", "callDuration: " + duration);
//            }
//        });
//        SipCmc.loginAccount(userInfo.getUserName(), BuildConfig.DOMAIN_CTEL, BuildConfig.AUTH_CTEL);

    }

    @Override
    public void onNetworkChange(int netMobile) {

    }
}

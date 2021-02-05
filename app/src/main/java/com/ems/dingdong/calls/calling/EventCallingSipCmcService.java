package com.ems.dingdong.calls.calling;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.ems.dingdong.R;
import com.ems.dingdong.utiles.Constants;
import java.util.Objects;

public class EventCallingSipCmcService extends Service {

//    private String channelID = "SipService";
//    private static final int SERVICE_NOTIFICATION  = 1000001;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public void event() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("abc", "notification", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//        SipCmc.startService(this);
//        SipCmc.addCallback(null, new PhoneCallback() {
//            @Override
//            public void incomingCall(LinphoneCall linphoneCall) {
//                super.incomingCall(linphoneCall);
//
//                showServiceNotifiCation();
//                Toast.makeText(EventCallingSipCmc.this, "có cuộc gọi Ctel 1", Toast.LENGTH_SHORT).show();
//
//                //
//                Intent intent = new Intent(getApplicationContext(), CallingFragment.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "abc");
//                builder.setContentTitle("Cuộc gọi Ctel");
//                builder.setContentText("cuộc gọi đến"/*+ SipCmc.getCallerNumber()*/);
//                builder.setSmallIcon(R.drawable.ic_logo_ding_dong);
//                builder.setContentIntent(pendingIntent);
//                builder.setAutoCancel(true);
//
//                NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(getApplicationContext());
//                notificationCompat.notify(1, builder.build());
//
//            }
//        });
//    }
//
//    private void showServiceNotifiCation(){
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0/*requestCode*/, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification.Builder builder;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            builder = new Notification.Builder(this,channelID);
//        }else{
//            builder = new Notification.Builder(this);
//        }
//        builder.setSmallIcon(R.drawable.ic_logo_ding_dong)
//                ///.setContentTitle(getString(R.string.app_name))
//                .setContentText("Service Running")
//                .setContentIntent(contentIntent)
//                .build();// getNotification()
//        startForeground(SERVICE_NOTIFICATION, builder.build());
//    }


    /////
    private String CHANNEL_ID = "VoipChannel";
    private String CHANNEL_NAME = "Voip Channel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = null;
        if (intent != null && intent.getExtras() != null) {
            data = intent.getBundleExtra(Constants.FCM_DATA_KEY);
        }
        try {
            Intent receiveCallAction = new Intent(getApplicationContext(), EventCallingSipCmcService.class);
            receiveCallAction.putExtra(Constants.CALL_RESPONSE_ACTION_KEY, Constants.CALL_RECEIVE_ACTION);
            receiveCallAction.putExtra(Constants.FCM_DATA_KEY, data);
            receiveCallAction.setAction("RECEIVE_CALL");

            Intent cancelCallAction = new Intent(getApplicationContext(), EventCallingSipCmcService.class);
            cancelCallAction.putExtra(Constants.CALL_RESPONSE_ACTION_KEY, Constants.CALL_CANCEL_ACTION);
            cancelCallAction.putExtra(Constants.FCM_DATA_KEY, data);
            cancelCallAction.setAction("CANCEL_CALL");

            PendingIntent receiveCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1200, receiveCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent cancelCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1201, cancelCallAction, PendingIntent.FLAG_UPDATE_CURRENT);

            createChannel();
            NotificationCompat.Builder notificationBuilder = null;
            if (data != null) {
                notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentText(data.getString("remoteUserName"))
                        .setContentTitle("Incoming Voice Call")
                        .setSmallIcon(R.drawable.ic_call_hang_green)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .addAction(R.drawable.ic_call_hang_green, "Receive Call", receiveCallPendingIntent)
                        .addAction(R.drawable.ic_call_end_red, "Cancel call", cancelCallPendingIntent)
                        .setAutoCancel(true)
                        //.setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.voip_ringtone))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
                        .setFullScreenIntent(receiveCallPendingIntent, true);
            }

            Notification incomingCallNotification = null;
            if (notificationBuilder != null) {
                incomingCallNotification = notificationBuilder.build();
            }
            startForeground(120, incomingCallNotification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_STICKY;
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

}

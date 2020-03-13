package com.ems.dingdong.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.ems.dingdong.R;
import com.ems.dingdong.functions.login.LoginActivity;
import com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.XacNhanDiaChiActivity;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class DingDongFirebaseMessagingService extends FirebaseMessagingService {
    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final String TAG = DingDongFirebaseMessagingService.class.getSimpleName();
    private int numMessages = 0;
    private NotificationUtils notificationUtils;

  /*   "notification": {
        "click_action": ".fcm.NotificationActivity",
                "body": "Xin chào, tôi là bob!",
                "title": "DingDong",
                "android_channel_id":"channel_id_dingdong"
    },
            "data": {
        "body": "Xin chào, tôi là bob!",
                "title": "DingDong",
    }*/

    @Override
    public void onNewToken(@NonNull String refreshedToken) {
        super.onNewToken(refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

        SharedPref sharedPref = new SharedPref(this);
        sharedPref.putString(Constants.KEY_PUSH_NOTIFICATION, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            sendNotification(remoteMessage.getData().get("message"));
        } else if (notification != null) {
            Map<String, String> data = remoteMessage.getData();
            sendNotification(notification, data);
        }

       /*
       lưu ý data push từ server thế này sẽ có rung và chuông
       {
            "data": {
            "message": "Đã có tin mới"
            },
            "registration_ids": [
            "f0uneo9Jgy8:APA91bHQVTHPaPOnu5pLV5F__GHXe6CA5eTzY_ROzl_pnwPTeQrulYKsMas3etOUSyELSzZFdR3F5SkRnKBJHi-de-ARIcU-GaWrxCWtvuGUsruIL8pcrmkp1bx4UFuABWgFHRYNJMnkovLn4h7iDWcIXh-dho_2uw"
            ]
        }*/
    }

    private void sendNotification(String messageBody) {
        Intent intent;
        Bundle bundle = new Bundle();
        if (messageBody.contains(Constants.GOM_HANG)) {
            intent = new Intent(this, XacNhanDiaChiActivity.class);
            intent.putExtra(Constants.TYPE_GOM_HANG, 1);
        } else {
            intent = new Intent(this, ListBaoPhatBangKeActivity.class);
            intent.putExtra(Constants.TYPE_GOM_HANG, 3);
        }
        bundle.putString("message", messageBody);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        long[] vibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000, 2000};
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("Thông báo")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setVibrate(vibratePattern)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id),
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(getString(R.string.notification_channel_id));
        }
        if (notificationManager != null)
            notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        Bundle bundle = new Bundle();
        bundle.putString(FCM_PARAM, data.get(FCM_PARAM));

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long[] vibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000, 2000};
        Log.d("Content ===>", "Content: " + notification.getBody());
        Log.d("Title ===>", "Title: " + notification.getTitle());
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("Hello")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setLights(Color.RED, 1000, 300)
                // .setDefaults(Notification.DEFAULT_VIBRATE)
                .setVibrate(vibratePattern)
                .setNumber(++numMessages)
                .setSmallIcon(R.drawable.ic_notification);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(getString(R.string.notification_channel_id));
        }

        assert notificationManager != null;
        notificationManager.notify(1, notificationBuilder.build());
    }
}

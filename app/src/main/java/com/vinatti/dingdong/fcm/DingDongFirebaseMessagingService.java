package com.vinatti.dingdong.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.login.LoginActivity;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

public class DingDongFirebaseMessagingService extends FirebaseMessagingService {
    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final String TAG = DingDongFirebaseMessagingService.class.getSimpleName();
    private int numMessages = 0;
    private NotificationUtils notificationUtils;

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
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent intent = new Intent(this, LoginActivity.class);
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


            if (notificationManager != null)
                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationUtils = new NotificationUtils(getApplicationContext());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notificationUtils.showNotificationMessage("Thông báo", messageBody, new Date().getTime() + "", intent);
        }
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

        try {
            String picture = data.get(FCM_PARAM);
            if (picture != null && !"".equals(picture)) {
                URL url = new URL(picture);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }
}

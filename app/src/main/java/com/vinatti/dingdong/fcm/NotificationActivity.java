package com.vinatti.dingdong.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.login.LoginActivity;
import com.vinatti.dingdong.utiles.Constants;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_login);
      //  ButterKnife.bind(this);
        String message = getIntent().getStringExtra(Constants.KEY_MESSAGE);
        sendNotification(message);
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);*/

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        long[] vibratePattern = new long[]{1000, 1000, 1000, 1000, 1000};
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_notification)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification))
                        .setContentTitle("Thông báo")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setVibrate(vibratePattern)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        }
        if (notificationManager != null)
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

   /* @OnClick(R.id.tv_status)
    public void onViewClicked() {
        String message = getIntent().getStringExtra(Constants.KEY_MESSAGE);
        sendNotification(message);
    }*/
}

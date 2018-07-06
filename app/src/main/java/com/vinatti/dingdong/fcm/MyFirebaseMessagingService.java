package com.vinatti.dingdong.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vinatti.dingdong.R;
import com.vinatti.dingdong.functions.login.LoginActivity;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.NotificationUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private Vibrator mVibrator;
    private static final String TAG = "MyFirebaseMsgService";
    private PowerManager.WakeLock wakeLock;


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            handleNow(remoteMessage.getData().get("message"));

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    /**
     * Handle time allotted to BroadcastReceivers.
     *
     * @param message
     */
    private void handleNow(String message) {
        sendNotification(message);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    }
    @SuppressLint("InvalidWakeLockTag")
    @SuppressWarnings("deprecation")
    public void acquireWakeLock(Context context) {
        //neu khoa man hinh thi bat len
        if (wakeLock != null)
            wakeLock.release();

        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, Constants.TAG_WAKE_LOCK);

        wakeLock.acquire();
    }

    public void releaseWakeLock() {
        if (wakeLock != null)
            wakeLock.release();
        wakeLock = null;
    }
}

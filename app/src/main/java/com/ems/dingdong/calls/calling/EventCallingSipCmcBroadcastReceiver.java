package com.ems.dingdong.calls.calling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ems.dingdong.utiles.Constants;

public class EventCallingSipCmcBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra(Constants.CALL_RESPONSE_ACTION_KEY);
            Bundle data = intent.getBundleExtra(Constants.FCM_DATA_KEY);

            if (action != null) {
                performClickAction(context, action, data);
            }

            // Close the notification after the click action is performed.

            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
            context.stopService(new Intent(context, EventCallingSipCmcService.class));
        }
    }

    private void performClickAction(Context context, String action, Bundle data) {
        if (action.equals(Constants.CALL_RECEIVE_ACTION) && data != null && data.get("type").equals("voip")) {
            Intent openIntent = null;
            try {
                openIntent = new Intent(context, CallingFragment.class);
                        openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(openIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } /*else if (action.equals(Constants.CALL_RECEIVE_ACTION) && data != null && data.get("type").equals("video")) {
            Intent openIntent = null;
            try {
                openIntent = new Intent(context, VideoCallActivity.class);
                        openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(openIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }*/ else if (action.equals(Constants.CALL_CANCEL_ACTION)) {
            context.stopService(new Intent(context, EventCallingSipCmcService.class));
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
    }
}
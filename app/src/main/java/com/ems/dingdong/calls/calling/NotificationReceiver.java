package com.ems.dingdong.calls.calling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ems.dingdong.calls.IncomingCallActivity;
import com.ems.dingdong.utiles.Constants;
//import com.sip.cmc.SipCmc;

import org.greenrobot.eventbus.EventBus;


public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*String message = intent.getStringExtra("toastMessage");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();*/

        String action = intent.getAction();
        if (Constants.YES_ACTION.equals(action)) {
            //Toast.makeText(context, "YES CALLED", Toast.LENGTH_SHORT).show();
//            SipCmc.acceptCall();

            Intent i = new Intent(context, IncomingCallActivity.class);
            i.putExtra(Constants.CALL_TYPE, 1);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
        else  if (Constants.STOP_ACTION.equals(action)) {
            //Toast.makeText(context, "STOP CALLED", Toast.LENGTH_SHORT).show();
//            SipCmc.hangUp();
        }

    }
}

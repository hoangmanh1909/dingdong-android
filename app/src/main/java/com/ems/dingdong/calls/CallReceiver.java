package com.ems.dingdong.calls;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Constants;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

public class CallReceiver extends PhoneStateBroadcastReceiver {


    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        super.onIncomingCallStarted(ctx, number, start);

    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        super.onOutgoingCallStarted(ctx, number, start);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onIncomingCallEnded(ctx, number, start, end);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onOutgoingCallEnded(ctx, number, start, end);
        if(number!=null) {
            if (number.startsWith(Constants.HEADER_NUMBER_LOG)) {
                SharedPref sharedPref = new SharedPref(ctx);
                String callerNumber = "";
                String useiid = "";
                String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                if (!userJson.isEmpty()) {
                    UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                    callerNumber = userInfo.getMobileNumber();
                    useiid = userInfo.getiD();
                }
                //long diffInMs = end.getTime() - start.getTime();

               // long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

                String duration = lastCall(ctx);
                NetWorkController.addNewCallCenter(useiid, useiid, callerNumber, number.replaceFirst("159",""), duration + "",
                        DateTimeUtils.convertDateToString(end, DateTimeUtils.DEFAULT_DATETIME_FORMAT),
                        new CommonCallback<SimpleResult>(ctx) {
                            @Override
                            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                                super.onSuccess(call, response);
                            }

                            @Override
                            protected void onError(Call<SimpleResult> call, String message) {
                                super.onError(call, message);
                            }
                        });
            }
        }
    }

    public String lastCall(Context context) {
        StringBuffer sb = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        Cursor cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");

        //int number = cur.getColumnIndex( CallLog.Calls.NUMBER );
        int duration = cur.getColumnIndex(CallLog.Calls.DURATION);
        while (cur.moveToNext()) {
            //String phNumber = cur.getString( number );
            String callDuration = cur.getString(duration);
            sb.append(callDuration);
            break;
        }
        cur.close();
        String str = sb.toString();
        return str;
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        super.onMissedCall(ctx, number, start);
    }
}

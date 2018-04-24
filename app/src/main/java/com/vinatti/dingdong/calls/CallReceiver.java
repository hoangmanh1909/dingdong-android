package com.vinatti.dingdong.calls;

import android.app.Activity;
import android.content.Context;

import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.DateTimeUtils;
import com.vinatti.dingdong.utiles.SharedPref;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        if (number.equals(Constants.HOTLINE_CALL_SHOW)) {
            SharedPref sharedPref = new SharedPref(ctx);
            String callerNumber = "";
            String useiid = "";
            String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
            if (!userJson.isEmpty()) {
                UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                callerNumber = userInfo.getMobileNumber();
                useiid = userInfo.getiD();
            }
            long diffInMs = end.getTime() - start.getTime();

            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
            NetWorkController.addNewCallCenter(useiid, useiid, callerNumber, number, diffInSec + "",
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

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        super.onMissedCall(ctx, number, start);
    }
}

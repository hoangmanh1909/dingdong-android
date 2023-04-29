package com.ems.dingdong.services;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;


public class PushWork extends Worker {
    private static final String PROGRESS = "PROGRESS";
    private static final long DELAY = 1000L;

    public PushWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int number = 1;
        int number1 = 3;
        Log.d("THanhkhiem", "dowork : " + number + number1);
        SharedPref sharedPref = new SharedPref(getApplicationContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            final String postmanID = userInfo.getiD();
            NetWorkControllerGateWay.locationAddNew(postmanID, "", "", new CommonCallback<SimpleResult>(getApplicationContext()) {
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
        return Result.retry();
    }
}

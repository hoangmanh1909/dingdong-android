package com.ems.dingdong.functions.mainhome.calllog;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ems.dingdong.R;
import com.ems.dingdong.app.logcall.CallLogInfo;
import com.ems.dingdong.app.logcall.CallLogUtils;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.CustomToast;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CallLogService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Calendar mCalendarRaCa;
    private Calendar mCalendarVaoCa;
    private String mDateRaCa;
    private String mDateVaoCa;
    String mDataCA;
    UserInfo userInfo1 = null;
    SharedPref sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPref = new SharedPref(getApplication());
        mCalendarRaCa = Calendar.getInstance();
        loadData();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }
    public void loadData() {
        try {
            CallLogUtils callLogUtils = CallLogUtils.getInstance(getApplication());
            List<CallLogInfo> list = callLogUtils.readCallLogs();
            mCalendarRaCa = Calendar.getInstance();
            sharedPref = new SharedPref(getApplication());
            mDataCA = sharedPref.getString(Constants.KEY_RA_VAOV1, "");
            try {
                if (!mDataCA.isEmpty()) {
                    mDateRaCa = DateTimeUtils.convertDateToString(mCalendarRaCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                    List<CallLogInfo> modeList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        long dateFrom = list.get(i).getDate();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                        String date = formatter.format(dateFrom);
                        @SuppressLint("SimpleDateFormat") Date date1 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(date);
                        @SuppressLint("SimpleDateFormat") Date date2 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(mDataCA);
                        @SuppressLint("SimpleDateFormat") Date date3 = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT).parse(mDateRaCa);
                        if (date1.compareTo(date2) >= 0 && date1.compareTo(date3) <= 0) {
                            modeList.add(list.get(i));
                        }
                    }
                    String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                    if (!userJson.isEmpty()) {
                        userInfo1 = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                    }
                    List<CallLogMode> request = new ArrayList<>();
                    if (modeList.size() > 0) {
                        for (CallLogInfo info : modeList) {
                            CallLogMode i = new CallLogMode();
                            long dateFrom = info.getDate();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATETIME_FORMAT);
                            String date = formatter.format(dateFrom);
                            i.setDate(date);
                            i.setCallDate(date);
                            i.setCallDuration(String.valueOf(info.getDuration()));
                            i.setCallType(Integer.parseInt(info.getCallType()));
                            i.setPhNumber(info.getNumber());
                            i.setPostmanCode(userInfo1.getUserName());
                            i.setFromNumber(userInfo1.getMobileNumber());
                            request.add(i);
                        }
                        try {
                            if (request.size() > 0)
                                callApi(request);
                        } catch (Exception e) {
                            Toast.showToast(getApplicationContext(), "GetCallLog");
                        }
                    } else {
                        CustomToast.makeText(getApplicationContext(), (int) CustomToast.LONG, "Bạn không thực hiện cuộc gọi nào (từ " + mDataCA + " đến " + mDateRaCa + ")",
                                Constants.ERROR).show();
                        mCalendarVaoCa = Calendar.getInstance();
                        sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                        Log.d("qưeqwe11231", DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                    }
                } else {
                    mCalendarVaoCa = Calendar.getInstance();
                    sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
                    CustomToast.makeText(getApplicationContext(), (int) CustomToast.LONG, "Ghi nhận lần đăng nhập lúc " + DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT), Constants.ERROR).show();
                }
            } catch (Exception e) {
                e.getMessage();
                sharedPref.putBoolean(Constants.KEY_TRANG_THAI_LOG_CALL, true);
                CustomToast.makeText(getApplicationContext(), (int) CustomToast.LONG, "Lỗi không thể ghi nhận được log cuộc gọi. (" + e.getMessage() + ")", Constants.ERROR).show();
            }
        } catch (Exception e) {
            Toast.showToast(getApplicationContext(), "1: K " + e.getMessage());
        }
    }

    private void callApi(List<CallLogMode> request) {
        NetWorkControllerGateWay.getCallLog(request)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode() != null)
                        if (simpleResult.getErrorCode().equals("00")) {
                            showCallLog(request.size());
//                            mView.hideProgress();
                        } else {
                            Toast.showToast(getApplicationContext(), simpleResult.getMessage());
                        }
                }, throwable -> {
//                    mView.hideProgress();
                    new ApiDisposable(throwable, getApplicationContext());
                });
    }

    public void showCallLog(int size) {
        try {
            sharedPref = new SharedPref(getApplication());
            mCalendarVaoCa = Calendar.getInstance();
            sharedPref.putString(Constants.KEY_RA_VAOV1, DateTimeUtils.convertDateToString(mCalendarVaoCa.getTime(), DateTimeUtils.DEFAULT_DATETIME_FORMAT));
            CustomToast.makeText(getApplicationContext(), (int) CustomToast.LONG, "Ghi nhận thành công " + size + " cuộc gọi lên hệ thống", Constants.SUCCESS).show();
        } catch (Exception e) {
            Toast.showToast(getApplicationContext(), ":Log");
        }

    }

}

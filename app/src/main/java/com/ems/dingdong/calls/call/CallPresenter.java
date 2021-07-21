package com.ems.dingdong.calls.call;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.profile.CustomCallerInAndSessonIdIn;
import com.ems.dingdong.functions.mainhome.profile.CustomItem;
import com.ems.dingdong.functions.mainhome.profile.CustomLadingCode;
import com.ems.dingdong.functions.mainhome.profile.CustomToNumber;
import com.ems.dingdong.model.CallHistoryVHT;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Response;

public class CallPresenter extends Presenter<CallContract.View, CallContract.Interactor> implements CallContract.Presenter {

    String ladingCode = "";
    String toNumber = "";
    String numberCustomer = "";
    String xSessionIdIn = "";

    private String callId;
    private int type;
    private int apptoapp;
    private String callerNumber;
    private String calleeNumber;
    private long sessionId;
    private CallHistoryVHT CallHistoryVHT;

    public CallPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public CallPresenter setCallId(String callId) {
        this.callId = callId;
        return this;
    }

    public CallPresenter setType(int type) {
        this.type = type;
        return this;
    }

    public CallPresenter setAppToApp(int apptoapp) {
        this.apptoapp = apptoapp;
        return this;
    }

    public CallPresenter setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
        return this;
    }

    public CallPresenter setCalleeNumber(String calleeNumber) {
        this.calleeNumber = calleeNumber;
        return this;
    }

    public CallPresenter setSessionId(Long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    @Override
    public CallContract.Interactor onCreateInteractor() {
        return new CallInteractor(this);
    }

    @Override
    public CallContract.View onCreateView() {
        return CallFragment.getInstance();
    }

    @Override
    public void setCallType(int type) {
        this.type = type;
    }

    @Override
    public String getCallId() {
        return callId;
    }

    @Override
    public int getCallType() {
        return type;
    }

    @Override
    public int getAppToApp() {
        return apptoapp;
    }

    @Override
    public String getCallerNumber() {
        return callerNumber;
    }

    @Override
    public String getCalleeNumber() {
        return calleeNumber;
    }

    @Subscribe(sticky = true)
    public void onEvent(CustomItem customItem) {
    }

    @Subscribe(sticky = true)
    public void onEventToNumber(CustomToNumber customToNumber) {
        toNumber = customToNumber.getMessage();
    }

    @Subscribe(sticky = true)
    public void onEventLadingCode(CustomLadingCode customLadingCode) {
        ladingCode = customLadingCode.getMessage();
    }

    @Subscribe(sticky = true)
    public void onEventCustomer(CustomCallerInAndSessonIdIn customCallerInAndSessonIdIn) {
        numberCustomer = customCallerInAndSessonIdIn.getNumberCustomer();
        xSessionIdIn = customCallerInAndSessonIdIn.getSessionId();
    }


    @Override
    public void callForward(String phone, String parcelCode) {

        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        addCallback(mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showCallSuccess();
                    } else {
                        mView.showCallError(response.body().getMessage());
                    }
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showCallError(message);
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable error) {
                super.onFailure(call, error);
                mView.showCallError("Lỗi kết nối đến tổng đài");
            }
        }));

    }


}

package com.ems.dingdong.calls.calling;

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
import com.ems.dingdong.model.response.ResponseObject;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.Gson;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Response;

public class CallingPresenter extends Presenter<CallingContract.View, CallingContract.Interactor> implements CallingContract.Presenter {

    String xSessionIdPostmanOut = "";
    UserInfo userInfo;
    PostOffice postOffice;
    RouteInfo routeInfo;
    private CallingFragment.TypeBD13 mTypeBD13;
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

    public CallingPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public CallingPresenter setCallId(String callId) {
        this.callId = callId;
        return this;
    }

    public CallingPresenter setType(int type) {
        this.type = type;
        return this;
    }

    public CallingPresenter setAppToApp(int apptoapp) {
        this.apptoapp = apptoapp;
        return this;
    }

    public CallingPresenter setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
        return this;
    }

    public CallingPresenter setCalleeNumber(String calleeNumber) {
        this.calleeNumber = calleeNumber;
        return this;
    }

    public CallingPresenter setSessionId(Long sessionId){
        this.sessionId = sessionId;
        return this;
    }

    @Override
    public CallingContract.Interactor onCreateInteractor() {
        return new CallingInteractor(this);
    }

    @Override
    public CallingContract.View onCreateView() {
        return CallingFragment.getInstance();
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

    @Override
    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public void createCallHistoryVHTOut() {
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        //Lịch sử gọi đi
        //if (type == Constants.CALL_TYPE_HISTORY_POSTMAN_OUT) {
            CallHistoryVHT = new CallHistoryVHT(ladingCode, userInfo.getPOProvinceCode(), userInfo.getPODistrictCode(), userInfo.getUnitCode(), userInfo.getUserName(), routeInfo.getRouteCode(), userInfo.getMobileNumber(), toNumber, "", xSessionIdPostmanOut);
            Log.d("123123", "ladingCodes: " + ladingCode + " province: " + userInfo.getPOProvinceCode() + " district: " + userInfo.getPODistrictCode() + " po code: " + userInfo.getUnitCode() + " id postman: " + userInfo.getUserName() + " route code: " + routeInfo.getRouteCode() + " sdt ng gọi: " + userInfo.getMobileNumber() + " toNumber: " + toNumber + " xSessionIdPostmanOut:" + xSessionIdPostmanOut);
            Gson gson = new Gson();
            String json = gson.toJson(CallHistoryVHT);

    }

    //lịch gửi gọi đến
    @Override
    public void createCallHistoryVHTIn() {
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }

        CallHistoryVHT = new CallHistoryVHT("", userInfo.getPOProvinceCode(), userInfo.getPODistrictCode(), userInfo.getUnitCode(), userInfo.getUserName(), routeInfo.getRouteCode(), numberCustomer, userInfo.getMobileNumber(), "", xSessionIdIn);
        //Log.d("123123", "ladingCode: " + "" + " province: " + userInfo.getPOProvinceCode() + " district: " + userInfo.getPODistrictCode() + " po code: " + userInfo.getUnitCode() + " id postman: " + userInfo.getUserName() + " route code: " + routeInfo.getRouteCode() + " sdt ng gọi: " + numberCustomer + " toNumber: " + userInfo.getMobileNumber() + " xSessionIdIn: " + xSessionIdIn);

        Gson gsons = new Gson();
        String jsons = gsons.toJson(CallHistoryVHT);

    }

    @Subscribe(sticky = true)
    public void onEvent(CustomItem customItem){
        xSessionIdPostmanOut = customItem.getMessage();

    }

    @Subscribe(sticky =  true)
    public void onEventToNumber(CustomToNumber customToNumber){
        toNumber = customToNumber.getMessage();
    }

    @Subscribe(sticky =  true)
    public void onEventLadingCode(CustomLadingCode customLadingCode){
        ladingCode = customLadingCode.getMessage();
    }

    @Subscribe(sticky = true)
    public void onEventCustomer(CustomCallerInAndSessonIdIn customCallerInAndSessonIdIn){
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

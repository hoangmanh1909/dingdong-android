package com.ems.dingdong.functions.login;


import android.net.Network;

import com.core.base.viper.Interactor;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.response.ResponseObject;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

/**
 * The Login interactor
 */
class LoginInteractor extends Interactor<LoginContract.Presenter>
        implements LoginContract.Interactor {

    LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void login(LoginRequest loginRequest, CommonCallback<LoginResult> commonCallback) {
        NetWorkControllerGateWay.loginAuthorized(loginRequest, commonCallback);
//        NetWorkController.loginAuthorized(loginRequest.getMobileNumber(), loginRequest., BuildConfig.VERSION_NAME, "", signature);
    }

    @Override
    public void login1(LoginRequest loginRequest, CommonCallback<LoginResult> commonCallback) {
//        NetWorkController.loginAuthorized(loginRequest.getMobileNumber(), loginRequest.getSignCode(), BuildConfig.VERSION_NAME, "", signature);
    }

    @Override
    public void getPostOfficeByCode(String code, String postmanID, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.getPostOfficeByCode(code, postmanID, callback);
    }

    @Override
    public void getSolutions(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getSolutions(commonCallback);
    }

    @Override
    public void getReasons(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasons(commonCallback);
    }

    @Override
    public void getVersion(CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.getVersion(callback);
    }

    @Override
    public Single<SimpleResult> getList(String data) {
        return NetWorkControllerGateWay.getListBuuCucHuyen(data);
    }

    @Override
    public Single<SimpleResult> getDanhSachNganHang() {
        return NetWorkControllerGateWay.getDanhSachNganHang();
    }

    @Override
    public void getBalance(String mobileNumber, String postmanId, CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getBalance(mobileNumber,postmanId ,commonCallback);
    }
}

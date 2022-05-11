package com.ems.dingdong.functions.login;


import android.net.Network;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.response.ResponseObject;
import com.ems.dingdong.network.NetWorkController;

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
    public void login(LoginRequest loginRequest, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.loginAuthorized(loginRequest, commonCallback);
    }

    @Override
    public void getPostOfficeByCode(String code, String postmanID, CommonCallback<SimpleResult> callback) {
        NetWorkController.getPostOfficeByCode(code, postmanID, callback);
    }

    @Override
    public void getSolutions(CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.getSolutions(commonCallback);
    }

    @Override
    public void getReasons(CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }

    @Override
    public void getVersion(CommonCallback<SimpleResult> callback) {
        NetWorkController.getVersion(callback);
    }

    @Override
    public Single<SimpleResult> getList(String data) {
        return NetWorkController.getListBuuCucHuyen(data);
    }

    @Override
    public void getBalance(String mobileNumber,String postmanId ,CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.getBalance(mobileNumber,postmanId ,commonCallback);
    }
}

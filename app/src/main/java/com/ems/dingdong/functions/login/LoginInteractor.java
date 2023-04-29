package com.ems.dingdong.functions.login;


import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.ActiveRequest;
import com.ems.dingdong.model.request.GetPostOfficeByCodeRequest;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.request.ValidationRequest;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.network.api.ApiService;
import com.ems.dingdong.model.request.LoginRequest;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * The Login interactor
 */
class LoginInteractor extends Interactor<LoginContract.Presenter>
        implements LoginContract.Interactor {

    LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }

//    @Override
//    public Observable<LoginResult> login(LoginRequest loginRequest, CommonCallback<LoginResult> commonCallback) {
//        return NetWorkControllerGateWay.loginAuthorized(loginRequest, commonCallback);
//    }
//
//
//    @Override
//    public void getPostOfficeByCode(String code, String postmanID, CommonCallback<SimpleResult> callback) {
//        NetWorkControllerGateWay.getPostOfficeByCode(code, postmanID, callback);
//    }
//
////    @Override
////    public void getSolutions(CommonCallback<SimpleResult> commonCallback) {
////        NetWorkControllerGateWay.getSolutions(commonCallback);
////    }
//
    @Override
    public void getReasons(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasons(commonCallback);
    }
//
//    @Override
//    public void getVersion(CommonCallback<SimpleResult> callback) {
//        NetWorkControllerGateWay.getVersion(callback);
//    }

    @Override
    public Single<SimpleResult> getList(String data) {
        return NetWorkControllerGateWay.getListBuuCucHuyen(data);
    }

    @Override
    public Single<SimpleResult> getDanhSachNganHang() {
        return NetWorkControllerGateWay.getDanhSachNganHang();
    }

    @Override
    public Observable<LoginResult> ddLogin(LoginRequest loginRequest) {
        return ApiService.ddLogin(loginRequest);
    }

    @Override
    public Observable<SimpleResult> ddvalidationAuthorized(ValidationRequest validationRequest) {
        return ApiService.ddXacThuc(validationRequest);
    }

    @Override
    public Observable<SimpleResult> ddLoginSms(ActiveRequest activeRequest) {
        return ApiService.ddLoginSms(activeRequest);
    }

    @Override
    public Observable<SimpleResult> ddPostOfficeByCode(GetPostOfficeByCodeRequest getPostOfficeByCodeRequest) {
        return ApiService.ddPostOfficeByCode(getPostOfficeByCodeRequest);
    }

    @Override
    public Observable<SimpleResult> ddGetVersion() {
        return ApiService.ddGetVersion();
    }
//    @Override
//    public Single<SimpleResult> getCallLog(List<CallLogMode> request) {
//        return NetWorkControllerGateWay.getCallLog(request);
//    }

//    @Override
//    public void getBalance(String mobileNumber, String postmanId, CommonCallback<SimpleResult> commonCallback) {
//        NetWorkControllerGateWay.getBalance(mobileNumber,postmanId ,commonCallback);
//    }
}

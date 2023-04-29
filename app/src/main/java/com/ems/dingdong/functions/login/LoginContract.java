package com.ems.dingdong.functions.login;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.ActiveRequest;
import com.ems.dingdong.model.request.GetPostOfficeByCodeRequest;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.request.ValidationRequest;
import com.ems.dingdong.model.response.GetVersionResponse;
import com.ems.dingdong.model.response.ResponseObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * The Login Contract
 */
interface LoginContract {

    interface Interactor extends IInteractor<Presenter> {
        //        Observable<LoginResult> login(LoginRequest loginRequest, CommonCallback<LoginResult> commonCallback);
//
//        void getPostOfficeByCode(String code, String postmanID, CommonCallback<SimpleResult> callback);
//
        void getReasons(CommonCallback<SimpleResult> commonCallback);
//
//        void getVersion(CommonCallback<SimpleResult> callback);

        Single<SimpleResult> getList(String data);

        Single<SimpleResult> getDanhSachNganHang();

        Observable<LoginResult> ddLogin(LoginRequest loginRequest);

        Observable<SimpleResult> ddvalidationAuthorized(ValidationRequest validationRequest);

        Observable<SimpleResult> ddLoginSms(ActiveRequest activeRequest);

        Observable<SimpleResult> ddPostOfficeByCode(GetPostOfficeByCodeRequest getPostOfficeByCodeRequest);

        Observable<SimpleResult> ddGetVersion();

    }

    interface View extends PresentView<Presenter> {
        //        void showMessage(String message);
//
        void showError(String message);
//
//        void gotoHome();
//
//        void showThanhCong();
//
//        void showVersionV1(List<GetVersionResponse> list);

        void showThanhCong();

        void showThatBai();

        void showSMS();

        void showXacThuc();

        void showLogin();

        void gotoLogin();

        void showVersion(List<GetVersionResponse> list);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        //        void login(String mobileNumber, String signCode);
//
//        //XAC THUC
//        void gotoValidation();
//
//        void getVersion();
//
        void ddLogin(String mobileNumber, String signCode);

        void ddXacThuc(String mobileNumber);

        void ddLoginSms(String phone, String smscode, String token);

        void ddGetVersion();

        void ddPostOfficeByCode(String unitCode, String postmanID);

        void getList(String data);

        //
        void getDanhSachNganHang();

        void getReasons();
    }
}




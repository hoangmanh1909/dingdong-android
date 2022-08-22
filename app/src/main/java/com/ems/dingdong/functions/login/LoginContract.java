package com.ems.dingdong.functions.login;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.response.ResponseObject;

import io.reactivex.Single;

/**
 * The Login Contract
 */
interface LoginContract {

    interface Interactor extends IInteractor<Presenter> {
        void login(LoginRequest loginRequest, CommonCallback<SimpleResult> commonCallback);

        void getPostOfficeByCode(String code, String postmanID, CommonCallback<SimpleResult> callback);

        void getSolutions(CommonCallback<SimpleResult> commonCallback);

        void getReasons(CommonCallback<SimpleResult> commonCallback);

        void getVersion(CommonCallback<SimpleResult> callback);

        Single<SimpleResult> getList(String data);

        Single<SimpleResult> getDanhSachNganHang();

        void getBalance(String mobileNumber,String postmanId,CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showMessage(String message);

        void showError(String message);

        void showVersion(String version, String urlDownload);

        void gotoHome();

        void showThanhCong();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void login(String mobileNumber, String signCode);

        void gotoValidation();

        void getVersion();

        void getList(String data);

        void getDanhSachNganHang();
    }
}




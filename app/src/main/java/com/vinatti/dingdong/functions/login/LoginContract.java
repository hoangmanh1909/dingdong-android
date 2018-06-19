package com.vinatti.dingdong.functions.login;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;

/**
 * The Login Contract
 */
interface LoginContract {

    interface Interactor extends IInteractor<Presenter> {
        void login(String mobileNumber, String signCode, CommonCallback<LoginResult> commonCallback);
        void getPostOfficeByCode(String code, String postmanID, CommonCallback<PostOfficeResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showMessage(String message);

        void showError(String message);

        void gotoHome();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void login(String mobileNumber, String signCode);

        void gotoValidation();
    }
}




package com.ems.dingdong.functions.login;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SolutionResult;

/**
 * The Login Contract
 */
interface LoginContract {

    interface Interactor extends IInteractor<Presenter> {
        void login(String mobileNumber, String signCode, CommonCallback<LoginResult> commonCallback);
        void getPostOfficeByCode(String code, String postmanID, CommonCallback<PostOfficeResult> callback);

        void getSolutions(CommonCallback<SolutionResult> commonCallback);

        void getReasons(CommonCallback<ReasonResult> commonCallback);
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




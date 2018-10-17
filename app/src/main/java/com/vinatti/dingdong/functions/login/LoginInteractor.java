package com.vinatti.dingdong.functions.login;


import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The Login interactor
 */
class LoginInteractor extends Interactor<LoginContract.Presenter>
        implements LoginContract.Interactor {

    LoginInteractor(LoginContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void login(String mobileNumber, String signCode, CommonCallback<LoginResult> commonCallback) {
        NetWorkController.loginAuthorized(mobileNumber, signCode, commonCallback);
    }

    @Override
    public void getPostOfficeByCode(String code, String postmanID, CommonCallback<PostOfficeResult> callback) {
        NetWorkController.getPostOfficeByCode(code, postmanID, callback);
    }

    @Override
    public void getSolutions(CommonCallback<SolutionResult> commonCallback) {
        NetWorkController.getSolutions(commonCallback);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }
}

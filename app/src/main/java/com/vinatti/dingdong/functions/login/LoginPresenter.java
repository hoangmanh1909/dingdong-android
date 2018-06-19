package com.vinatti.dingdong.functions.login;


import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.functions.login.validation.ValidationPresenter;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Login Presenter
 */
public class LoginPresenter extends Presenter<LoginContract.View, LoginContract.Interactor>
        implements LoginContract.Presenter {

    public LoginPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LoginContract.View onCreateView() {
        return LoginFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public LoginContract.Interactor onCreateInteractor() {
        return new LoginInteractor(this);
    }

    @Override
    public void login(String mobileNumber, String signCode) {
        mInteractor.login(mobileNumber, signCode, new CommonCallback<LoginResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<LoginResult> call, Response<LoginResult> response) {
                super.onSuccess(call, response);

                if (response.body().getErrorCode().equals("00")) {
                    getPostOfficeByCode(response.body().getUserInfo().getUnitCode(),response.body().getUserInfo().getiD());
                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
                    sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(response.body().getUserInfo()));
                } else if (response.body().getErrorCode().equals("05")) {
                    mView.hideProgress();
                    mView.showMessage("Số điện thoại đã được kích hoạt ở thiết bị khác, xin vui lòng thực hiện kích hoạt lại trên thiết bị này.");
                } else {
                    mView.hideProgress();
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<LoginResult> call, String message) {
                mView.hideProgress();
                super.onError(call, message);
                mView.showError(message);
            }
        });
    }

    @Override
    public void gotoValidation() {
        new ValidationPresenter(mContainerView).pushView();
    }

    private void getPostOfficeByCode(String unitCode,String postmanID) {
        mInteractor.getPostOfficeByCode(unitCode, postmanID,new CommonCallback<PostOfficeResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<PostOfficeResult> call, Response<PostOfficeResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    // go to home
                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
                    sharedPref.putString(Constants.KEY_HOTLINE_NUMBER, response.body().getPostOffice().getHolineNumber());
                    sharedPref.putString(Constants.KEY_POST_OFFICE, NetWorkController.getGson().toJson(response.body().getPostOffice()));
                    mView.gotoHome();
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<PostOfficeResult> call, String message) {
                mView.hideProgress();
                super.onError(call, message);
                mView.showError(message);
            }
        });
    }
}

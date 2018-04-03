package com.vinatti.dingdong.functions.login.validation;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.functions.login.active.ActivePresenter;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.SimpleResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Validation Presenter
 */
public class ValidationPresenter extends Presenter<ValidationContract.View, ValidationContract.Interactor>
        implements ValidationContract.Presenter {

    public ValidationPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ValidationContract.View onCreateView() {
        return ValidationFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ValidationContract.Interactor onCreateInteractor() {
        return new ValidationInteractor(this);
    }

    @Override
    public void validationAuthorized(final String mobileNumber) {
        mView.showProgress();
        mInteractor.validationAuthorized(mobileNumber, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    new ActivePresenter(mContainerView).setMobileNumber(mobileNumber).pushView();
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call) {
                mView.hideProgress();
                super.onError(call);
            }
        });
    }


}

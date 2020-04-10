package com.ems.dingdong.functions.mainhome.profile.prepaid.register;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterPresenter extends Presenter<RegisterContract.View, RegisterContract.Interactor> implements RegisterContract.Presenter {

    public RegisterPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public RegisterContract.Interactor onCreateInteractor() {
        return new RegisterInteractor(this);
    }

    @Override
    public RegisterContract.View onCreateView() {
        return RegisterFragment.getInstance();
    }

    @Override
    public void register(String name, String id, String mobileNumber) {
        mInteractor.register(name, id, mobileNumber, new CommonCallback<SimpleResult>(getViewContext()) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body() != null && response.body().getErrorCode().equals("00")) {
                    mView.showSuccess(true);
                } else if (response.body() != null && response.body().getErrorCode().equals("01")) {
                    mView.showSuccess(false);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }
}

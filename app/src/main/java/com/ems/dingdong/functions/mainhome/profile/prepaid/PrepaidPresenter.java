package com.ems.dingdong.functions.mainhome.profile.prepaid;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.profile.prepaid.register.RegisterPresenter;
import com.ems.dingdong.model.ChangeRouteResult;
import com.ems.dingdong.model.HistoryPrepaidResult;
import com.ems.dingdong.model.PrepaidResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Logger;
import com.ems.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

public class PrepaidPresenter extends Presenter<PrepaidContract.View, PrepaidContract.Interactor> implements PrepaidContract.Presenter {
    public PrepaidPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public PrepaidContract.Interactor onCreateInteractor() {
        return new PrepaidInteractor(this);
    }

    @Override
    public PrepaidContract.View onCreateView() {
        return PrepaidFragment.getInstance();
    }

    @Override
    public void getPrepaidInfo() {
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        mInteractor.getPrepaidInfo(userInfo.getMobileNumber(), new CommonCallback<PrepaidResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<PrepaidResult> call, Response<PrepaidResult> response) {
                super.onSuccess(call, response);
                if (response.body() != null && "01".equals(response.body().getErrorCode())) {
                    mView.showRegisterView();
                } else if (response.body() != null && "00".equals(response.body().getErrorCode())) {
                    mView.showInfo(response.body().getValue());
                }
            }

            @Override
            protected void onError(Call<PrepaidResult> call, String message) {
                super.onError(call, message);
                Logger.d("chau", "sdasda");
            }
        });
    }

    @Override
    public void showRegisterView() {
        new RegisterPresenter(mContainerView).pushView();
    }

    @Override
    public void getHistory() {
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        mInteractor.getHistory(userInfo.getMobileNumber(), new CommonCallback<HistoryPrepaidResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<HistoryPrepaidResult> call, Response<HistoryPrepaidResult> response) {
                super.onSuccess(call, response);
            }

            @Override
            protected void onError(Call<HistoryPrepaidResult> call, String message) {
                super.onError(call, message);
            }
        });
    }
}

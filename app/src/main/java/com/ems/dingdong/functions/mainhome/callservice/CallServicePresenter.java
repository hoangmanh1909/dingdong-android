package com.ems.dingdong.functions.mainhome.callservice;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.callservice.history.HistoryCallPresenter;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Constants;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CallService Presenter
 */
public class CallServicePresenter extends Presenter<CallServiceContract.View, CallServiceContract.Interactor>
        implements CallServiceContract.Presenter {

    public CallServicePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public CallServiceContract.View onCreateView() {
        return CallServiceFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public CallServiceContract.Interactor onCreateInteractor() {
        return new CallServiceInteractor(this);
    }

    @Override
    public void callForward(String phone) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showCallSuccess();
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });

    }

    @Override
    public void pushHistory() {
        new HistoryCallPresenter(mContainerView).presentView();
    }
}

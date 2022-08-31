package com.ems.dingdong.functions.login.active;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ActiveResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Active Presenter
 */
public class ActivePresenter extends Presenter<ActiveContract.View, ActiveContract.Interactor>
        implements ActiveContract.Presenter {

    private String mMobileNumber;

    public ActivePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ActiveContract.View onCreateView() {
        return ActiveFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ActiveContract.Interactor onCreateInteractor() {
        return new ActiveInteractor(this);
    }

    @Override
    public void activeAuthorized(final String mobileNumber, String activeCode, String codeDeviceActive) {
        mView.showProgress();
        mInteractor.activeAuthorized(mobileNumber, activeCode, codeDeviceActive, new CommonCallback<ActiveResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ActiveResult> call, Response<ActiveResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ActiveResult activeResult = NetWorkController.getGson().fromJson(response.body().getData(), ActiveResult.class);
                    String value = mobileNumber + ";" + activeResult.getSignCode();
                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
                    sharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, value);

//                    Log.d("ASDASDASD",sharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE,""));
//                    String value = mobileNumber + ";" + response.body().getSignCode();
//                    SharedPref sharedPref = new SharedPref((Context) mContainerView);
//                    sharedPref.putString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, value);
                    back();
                    back();
                } else {
                    mView.showError(response.body().getMessage());
                }

            }

            @Override
            protected void onError(Call<ActiveResult> call, String message) {
                mView.hideProgress();
                super.onError(call, message);
                mView.showError(message);
            }
        });
    }

    @Override
    public String getMobileNumber() {
        return mMobileNumber;
    }

    public ActivePresenter setMobileNumber(String mobileNumber) {
        mMobileNumber = mobileNumber;
        return this;
    }


}

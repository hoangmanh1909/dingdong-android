package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The Phone Presenter
 */
public class PhonePresenter extends Presenter<PhoneContract.View, PhoneContract.Interactor>
        implements PhoneContract.Presenter {

    private String mPhone;
    private String mCode;

    public PhonePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PhoneContract.View onCreateView() {
        return PhoneFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public PhoneContract.Interactor onCreateInteractor() {
        return new PhoneInteractor(this);
    }

    public PhonePresenter setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    @Override
    public String getPhone() {
        return mPhone;
    }

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public void callForward(String phone) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, mCode, userInfo.getiD(), poCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showCallSuccess(response.body().getData());
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });

    }

    @Override
    public void updateMobile(String phone) {
        mView.showProgress();
        mInteractor.updateMobile(mCode, "2", phone, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                mView.showView(phone, response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void ddCall(CallLiveMode r) {
        mView.showProgress();
        mInteractor.ddCall(r)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showCallLive(r.getFromNumber());
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                    mView.hideProgress();
                });
    }

    public PhonePresenter setCode(String code) {
        mCode = code;
        return this;
    }
}

package com.ems.dingdong.functions.mainhome.callservice.history;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.HistoryCallInfo;
import com.ems.dingdong.model.HistoryCallResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Constants;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The History Presenter
 */
public class HistoryCallPresenter extends Presenter<HistoryCallContract.View, HistoryCallContract.Interactor>
        implements HistoryCallContract.Presenter {

    private String mParcelCode;

    public HistoryCallPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HistoryCallContract.View onCreateView() {
        return HistoryCallFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public HistoryCallContract.Interactor onCreateInteractor() {
        return new HistoryCallInteractor(this);
    }

    public HistoryCallPresenter setParcelCode(String parcelCode) {
        this.mParcelCode = parcelCode;
        return this;
    }

    @Override
    public String getParcelCode() {
        return mParcelCode;
    }

    @Override
    public void getHistory(String fromDate, String toDate) {
        mView.showProgress();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String useiid = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
            useiid = userInfo.getiD();
        }
        mInteractor.searchCallCenter(useiid, fromDate, toDate, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<HistoryCallInfo> historyCallInfos = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<HistoryCallInfo>>(){}.getType());
                    mView.showListSuccess(historyCallInfos);
                } else {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showListEmpty();
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
}

package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.thongke.history.HistoryPresenter;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Statistic Presenter
 */
public class StatisticPresenter extends Presenter<StatisticContract.View, StatisticContract.Interactor>
        implements StatisticContract.Presenter {

    private String mStatus;

    public StatisticPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public StatisticContract.View onCreateView() {
        return StatisticFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public StatisticContract.Interactor onCreateInteractor() {
        return new StatisticInteractor(this);
    }

    @Override
    public void search(String fromDate, String status, String shift) {
        String postmanID = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }
        mView.showProgress();
        mInteractor.searchDeliveryStatistic(fromDate, status, postmanID,shift, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showListSuccess(response.body().getList());

                } else {
                    mView.showListEmpty();
                }
            }

            @Override
            protected void onError(Call<CommonObjectListResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public String getStatus() {
        return mStatus;
    }

    @Override
    public void pushViewDetail(String parcelCode) {
        new HistoryPresenter(mContainerView).setParcelCode(parcelCode).pushView();
    }

    public StatisticPresenter setType(String status) {
        mStatus = status;
        return this;
    }
}

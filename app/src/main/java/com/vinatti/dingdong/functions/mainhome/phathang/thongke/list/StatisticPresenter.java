package com.vinatti.dingdong.functions.mainhome.phathang.thongke.list;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Statistic Presenter
 */
public class StatisticPresenter extends Presenter<StatisticContract.View, StatisticContract.Interactor>
        implements StatisticContract.Presenter {

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
    public void search(String fromDate, String status) {
        String postmanID = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }
        mView.showProgress();
        mInteractor.searchDeliveryStatistic(fromDate, status, postmanID, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showListSuccess(response.body().getList());

                } else {
                    mView.showErrorToast(response.body().getMessage());
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
}

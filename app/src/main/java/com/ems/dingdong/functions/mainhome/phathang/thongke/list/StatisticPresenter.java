package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.thongke.history.HistoryPresenter;
import com.ems.dingdong.functions.mainhome.phathang.thongke.tabs.OnTabListener;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Statistic Presenter
 */
public class StatisticPresenter extends Presenter<StatisticContract.View, StatisticContract.Interactor>
        implements StatisticContract.Presenter {

    private OnTabListener listener;

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
    public void search(String fromDate, String toDate, String status, String routeCode) {
        String postmanID = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }
        mView.showProgress();
        mInteractor.searchDeliveryStatistic(fromDate, toDate, status, postmanID, routeCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                assert response.body() != null;
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<CommonObject> commonObjects = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<CommonObject>>(){}.getType());
                    mView.showListSuccess(commonObjects);

                } else {
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

    @Override
    public String getStatus() {
        return mStatus;
    }

    @Override
    public void pushViewDetail(String parcelCode) {
        new HistoryPresenter(mContainerView).setParcelCode(parcelCode).pushView();
    }

    @Override
    public void setCount(int count) {
        if ("C14".equals(mStatus))
            listener.onQuantityChanged(count, 0);
        else
            listener.onQuantityChanged(count, 1);
    }

    @Override
    public void onSearched(String fromDate, String toDate) {
        if ("C14".equals(mStatus)) {
            listener.onSearched(fromDate, toDate, 1);
        } else {
            listener.onSearched(fromDate, toDate, 0);
        }
    }

    public StatisticPresenter setType(String status) {
        mStatus = status;
        return this;
    }

    public StatisticPresenter setTabListener(OnTabListener listener) {
        this.listener = listener;
        return this;
    }
}

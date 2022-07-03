package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.detail.StatisticDebitDetailPresenter;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticDebitGeneralResult;
import com.ems.dingdong.model.response.StatisticDebitGeneralResponse;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Response;

public class StatisticDebitPresenter extends Presenter<StatisticDebitContract.View, StatisticDebitContract.Interactor>
        implements StatisticDebitContract.Presenter {

    private String mPostmanID;
    private String mFromDate;
    private String mToDate;

    public StatisticDebitPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public StatisticDebitContract.Interactor onCreateInteractor() {
        return new StatisticDebitInteractor(this);
    }

    @Override
    public StatisticDebitContract.View onCreateView() {
        return StatisticDebitFragment.getInstance();
    }

    @Override
    public void showStatistic(String postmanID, String fromDate, String toDate, String routeCode) {
        mFromDate = fromDate;
        mToDate = toDate;
        mInteractor.getDebitStatistic(postmanID, mFromDate, mToDate, routeCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    StatisticDebitGeneralResponse statisticDebitGeneralResponse = NetWorkControllerGateWay.getGson().fromJson(response.body().getData(), new TypeToken<StatisticDebitGeneralResponse>(){}.getType());
                    mView.showStatistic(statisticDebitGeneralResponse);
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
    public void showDetail(String statusCode, String fromDate, String toDate) {
        new StatisticDebitDetailPresenter(mContainerView).setIsSuccess(statusCode).setmFromDate(mFromDate).setmToDate(mToDate).pushView();
    }
}

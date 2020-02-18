package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.detail.StatisticDebitDetailPresenter;
import com.ems.dingdong.model.StatisticDebitGeneralResult;

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
    public void showStatistic(String postmanID, String fromDate, String toDate) {
        mInteractor.getDebitStatistic(postmanID, fromDate, toDate, new CommonCallback<StatisticDebitGeneralResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<StatisticDebitGeneralResult> call, Response<StatisticDebitGeneralResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showStatistic(response.body().getStatisticDebitGeneralResponses());
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<StatisticDebitGeneralResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void showDetail(String statusCode) {
        new StatisticDebitDetailPresenter(mContainerView).setIsSuccess(statusCode).pushView();
    }
}

package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.detail;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.utiles.Utils;

import retrofit2.Call;
import retrofit2.Response;

public class StatisticDebitDetailPresenter extends Presenter<StatisticDebitDetailContract.View, StatisticDebitDetailContract.Interactor>
        implements StatisticDebitDetailContract.Presenter {

    private String mStatusCode = "N";
    private String mPostmanID;
    private String mFromDate;
    private String mToDate;


    public StatisticDebitDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
    }

    @Override
    public StatisticDebitDetailContract.Interactor onCreateInteractor() {
        return new StatisticDebitDetailInteractor(this);
    }

    @Override
    public StatisticDebitDetailContract.View onCreateView() {
        return StatisticDebitDetailFragment.getInstance();
    }

    @Override
    public void statisticDebitDetail(String postmanID, String fromDate, String toDate) {
        mPostmanID = postmanID;
        mFromDate = fromDate;
        mToDate = toDate;
        mView.showProgress();
        mInteractor.statisticDebitDetail(postmanID, fromDate, toDate, mStatusCode, new CommonCallback<StatisticDebitDetailResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<StatisticDebitDetailResult> call, Response<StatisticDebitDetailResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showListDetail(Utils.getGeneralDebitDetailList(response.body().getStatisticDebitDetailResponses()));
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<StatisticDebitDetailResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void showDetail() {

    }

    @Override
    public String getStatusCode() {
        return mStatusCode;
    }

    public StatisticDebitDetailPresenter setIsSuccess(String statusCode) {
        this.mStatusCode = statusCode;
        return this;
    }
}

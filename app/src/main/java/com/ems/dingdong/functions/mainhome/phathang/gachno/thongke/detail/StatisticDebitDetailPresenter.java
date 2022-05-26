package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.detail;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.model.response.StatisticDebitDetailResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

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
    public void statisticDebitDetail(String postmanID, String routeCode) {
        mPostmanID = postmanID;
        mView.showProgress();
        mInteractor.statisticDebitDetail(postmanID, mFromDate, mToDate, mStatusCode, routeCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<StatisticDebitDetailResponse> arrayList = NetWorkControllerGateWay.getGson().fromJson(response.body().getData(), new TypeToken<List<StatisticDebitDetailResponse>>(){}.getType());
                    mView.showListDetail(Utils.getGeneralDebitDetailList(arrayList));
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

    public StatisticDebitDetailPresenter setmFromDate(String mFromDate) {
        this.mFromDate = mFromDate;
        return this;
    }

    public StatisticDebitDetailPresenter setmToDate(String mToDate) {
        this.mToDate = mToDate;
        return this;
    }
}

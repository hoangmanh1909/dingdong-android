package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess;

import android.app.Activity;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail.ListDeliverySuccessDetailPresenter;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.model.response.StatisticDeliveryGeneralResponse;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The History Presenter
 */
public class HistoryDetailSuccessPresenter extends Presenter<HistoryDetailSuccessContract.View, HistoryDetailSuccessContract.Interactor>
        implements HistoryDetailSuccessContract.Presenter {

    private String mPostmanID;
    private String mFromDate;
    private String mToDate;

    private StatisticType mStatisticType;

    public HistoryDetailSuccessPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public HistoryDetailSuccessContract.View onCreateView() {
        return HistoryDetailSuccessFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public HistoryDetailSuccessContract.Interactor onCreateInteractor() {
        return new HistoryDetailSuccessInteractor(this);
    }


    @Override
    public void statisticDeliveryGeneral(String postmanID, String fromDate, String toDate, String routeCode) {
        mPostmanID = postmanID;
        mFromDate = fromDate;
        mToDate = toDate;
        mView.showProgress();
        mInteractor.statisticDeliveryGeneral(postmanID, fromDate, toDate, mStatisticType, routeCode, new CommonCallback<StatisticDeliveryGeneralResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<StatisticDeliveryGeneralResult> call, Response<StatisticDeliveryGeneralResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
//                    ArrayList<StatisticDeliveryGeneralResponse> deliveryGeneralResponses = NetWorkControllerGateWay.getGson().fromJson(response.body().getData(),new TypeToken<ArrayList<StatisticDeliveryGeneralResponse>>(){}.getType());
                    mView.showListSuccess(Utils.getGeneralList(response.body().getStatisticDeliveryGeneralResponses()));

                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<StatisticDeliveryGeneralResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void showDetail(String serviceCode, String serviceName, int typeDelivery) {
        new ListDeliverySuccessDetailPresenter(mContainerView).setData(serviceCode, serviceName, typeDelivery, mPostmanID, mFromDate, mToDate, mStatisticType).pushView();
    }

    @Override
    public StatisticType getStatisticType() {
        return mStatisticType;
    }


    public HistoryDetailSuccessPresenter setStatisticType(StatisticType statisticType) {
        this.mStatisticType = statisticType;
        return this;
    }
}

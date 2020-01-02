package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.model.StatisticDetailCollect;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The CommonObject Presenter
 */
public class ListDeliverySuccessDetailPresenter extends Presenter<ListDeliverySuccessDetailContract.View, ListDeliverySuccessDetailContract.Interactor>
        implements ListDeliverySuccessDetailContract.Presenter {


    private String mServiceCode;
    private String mServiceName;
    private int mTypeDelivery;
    private String mPostmanID;
    private String mFromDate;
    private String mToDate;
    public ListDeliverySuccessDetailPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public ListDeliverySuccessDetailContract.View onCreateView() {
        return ListDeliverySuccessDetailFragment.getInstance();
    }

    @Override
    public void start() {
        statisticDeliveryDetail();
    }

    @Override
    public ListDeliverySuccessDetailContract.Interactor onCreateInteractor() {
        return new ListDeliverySuccessDetailInteractor(this);
    }

    public void statisticDeliveryDetail() {
        mView.showProgress();
        mInteractor.statisticDeliveryDetail(mServiceCode, mTypeDelivery,mPostmanID, mFromDate, mToDate, new CommonCallback<StatisticDeliveryDetailResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<StatisticDeliveryDetailResult> call, Response<StatisticDeliveryDetailResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showListSuccess(response.body().getStatisticDeliveryDetailResponses());
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<StatisticDeliveryDetailResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }
    public ListDeliverySuccessDetailPresenter setData(String serviceCode, String serviceName, int typeDelivery, String postmanID, String fromDate, String toDate) {
        mServiceCode = serviceCode;
        mServiceName = serviceName;
        mTypeDelivery = typeDelivery;
        mPostmanID = postmanID;
        mFromDate = fromDate;
        mToDate = toDate;
        return this;
    }

    @Override
    public String getServiceName() {
        return mServiceName;
    }
}

package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.model.response.StatisticDeliveryDetailResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

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

    private StatisticType mStatisticType;

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
        SharedPref sharedPref = new SharedPref(getViewContext());
        String routeCode = NetWorkController.getGson().fromJson(sharedPref.getString(
                Constants.KEY_ROUTE_INFO, ""), RouteInfo.class).getRouteCode();
        mInteractor.statisticDeliveryDetail(mServiceCode, mTypeDelivery, mPostmanID, mFromDate,
                mToDate, mStatisticType, routeCode,
                new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call,
                                             Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mView.hideProgress();
                        if (response.body().getErrorCode().equals("00")) {
                            ArrayList<StatisticDeliveryDetailResponse> arrayList = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<ArrayList<StatisticDeliveryDetailResponse>>(){}.getType());
                            mView.showListSuccess(Utils.getGeneralDeliveryDetailList(arrayList));;
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

    public ListDeliverySuccessDetailPresenter setData(String serviceCode, String serviceName,
                                                      int typeDelivery, String postmanID, String fromDate,
                                                      String toDate, StatisticType statisticType) {
        mServiceCode = serviceCode;
        mServiceName = serviceName;
        mTypeDelivery = typeDelivery;
        mPostmanID = postmanID;
        mFromDate = fromDate;
        mToDate = toDate;
        mStatisticType = statisticType;
        return this;
    }

    @Override
    public String getServiceName() {
        return mServiceName;
    }

    @Override
    public StatisticType getStatisticType() {
        return mStatisticType;
    }
}

package com.ems.dingdong.functions.mainhome.phathang.thongke.sml;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.StatisticSMLDeliveryFailRequest;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SmartlockStatisticPresenter extends Presenter<SmartlockStatisticContract.View, SmartlockStatisticContract.Interactor>
        implements SmartlockStatisticContract.Presenter {
    private RouteInfo routeInfo;
    private UserInfo userInfo;
    private PostOffice postOffice;
    Calendar calCreate;

    public SmartlockStatisticPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {
        calCreate = Calendar.getInstance();
        int date = Integer.parseInt(DateTimeUtils.convertDateToString(calCreate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5));
        getData(date, date);
    }

    @Override
    public SmartlockStatisticContract.Interactor onCreateInteractor() {
        return new SmartlockStatisticInteractor(this);
    }

    @Override
    public SmartlockStatisticContract.View onCreateView() {
        return SmartlockStatisticFragment.getInstance();
    }

    @Override
    public void getData(int fromDate, int toDate) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");


        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        StatisticSMLDeliveryFailRequest request = new StatisticSMLDeliveryFailRequest();
        request.setType(1);
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setPostmanCode(userInfo.getUserName());
        request.setRouteCode(routeInfo.getRouteCode());
        request.setPOCode(postOffice.getCode());

        mInteractor.statisticSMLDeliveryFail(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        List<StatisticSMLDeliveryFailResponse> list = NetWorkController.getGson().fromJson(simpleResult.getData(), new TypeToken<List<StatisticSMLDeliveryFailResponse>>() {
                        }.getType());
                        mView.showListSuccess(list);
                    }else{
                        mView.showListSuccess(new ArrayList<>());
                    }
                }, throwable -> {
                    mView.hideProgress();
                    mView.showErrorToast(throwable.getMessage());
                });
    }
}

package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat;


import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListBaoPhatPresenter extends Presenter<ListBaoPhatContract.View, ListBaoPhatContract.Interactor> implements ListBaoPhatContract.Presenter {

    public ListBaoPhatPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ListBaoPhatContract.Interactor onCreateInteractor() {
        return new ListBaoPhatInterractor(this);
    }

    @Override
    public ListBaoPhatContract.View onCreateView() {
        return ListBaoPhatFragment.getInstance();
    }

    ListBaoPhatContract.OnTabListener tabListener;

    public ListBaoPhatPresenter setOnTabListener(ListBaoPhatContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String toDate, String routeCode, Integer deliveryType) {
        mView.showProgress();
        mInteractor.searchDeliveryPostman(postmanID, fromDate, toDate, routeCode, deliveryType)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        ArrayList<DeliveryPostman> deliveryPostmen = NetWorkController.getGson().fromJson(simpleResult.getData(), new TypeToken<List<DeliveryPostman>>() {
                        }.getType());
                        mView.showListSuccess(deliveryPostmen);
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.hideProgress();
                });
    }
}

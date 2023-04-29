package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.listdanhsachbaophat;


import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat.TabBaoPhatConstract;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKePresenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryConstract;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.network.ApiDisposable;
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

    private int mPos;

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

    TabBaoPhatConstract.OnTabsListener tabListener;

    TabBaoPhatConstract.OnDeliveryNotSuccessfulChange deliveryNotSuccessfulChange;

    public ListBaoPhatPresenter setOnTabListener(TabBaoPhatConstract.OnTabsListener listener) {
        this.tabListener = listener;
        return this;
    }

    public ListBaoPhatPresenter setDeliveryNotSuccessfulChange(TabBaoPhatConstract.OnDeliveryNotSuccessfulChange deliveryNotSuccessfulChange) {
        this.deliveryNotSuccessfulChange = deliveryNotSuccessfulChange;
        return this;
    }


    public ListBaoPhatPresenter setTypeTab(int position) {
        mPos = position;
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
                    new ApiDisposable(throwable, getViewContext());
                });
    }

    @Override
    public int getPositionTab() {
        return mPos;
    }

    @Override
    public void setTitleTab(int quantity) {
        tabListener.onQuantityChanged(quantity, 0);
    }
}

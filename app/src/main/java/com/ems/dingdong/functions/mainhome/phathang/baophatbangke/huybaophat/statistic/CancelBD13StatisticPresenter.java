package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13TabContract;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CancelBD13StatisticPresenter extends Presenter<CancelBD13StatisticContract.View,
        CancelBD13StatisticContract.Interactor> implements CancelBD13StatisticContract.Presenter {

    private CancelBD13TabContract.OnTabListener tabListener;

    public CancelBD13StatisticPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public CancelBD13StatisticContract.Interactor onCreateInteractor() {
        return new CancelBD13StatisticInteractor(this);
    }

    @Override
    public CancelBD13StatisticContract.View onCreateView() {
        return CancelBD13StatisticFragment.getInstance();
    }

    public CancelBD13StatisticPresenter setOnTabListener(CancelBD13TabContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }


    @Override
    public void getCancelDeliveryStatic(String poCode, String postmanCode, String routeCode, Integer fromDate, Integer toDate, String statusCode) {
        CancelDeliveryStatisticRequest request = new CancelDeliveryStatisticRequest();
        request.setpOCode(poCode);
        request.setPostmanCode(postmanCode);
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setStatusCode(statusCode);
        request.setRouteCode(routeCode);
        mView.showProgress();
        mInteractor.getCancelDeliveryStatic(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listStatistic -> {
                            if (listStatistic.getErrorCode().equals("00")) {
                                mView.showListSuccess(listStatistic.getStatisticItemList());
                            } else {
                                mView.showError(listStatistic.getMessage());
                            }
                        }
                        ,
                        error -> {
                            mView.showError(error.getMessage());
                        }
                );
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void titleChanged(int quantity, int currentSetTab) {
        tabListener.onQuantityChange(quantity, currentSetTab);
    }
}

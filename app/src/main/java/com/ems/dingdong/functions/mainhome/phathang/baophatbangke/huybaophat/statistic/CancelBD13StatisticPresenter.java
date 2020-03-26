package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CancelBD13StatisticPresenter extends Presenter<CancelBD13StatisticContract.View, CancelBD13StatisticContract.Interactor> implements CancelBD13StatisticContract.Presenter {

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

    @Override
    public void getCancelDeliveryStatic(String poCode, String postmanCode, String routeCode, Integer fromDate, Integer toDate, String statusCode) {
        CancelDeliveryStatisticRequest request = new CancelDeliveryStatisticRequest();
        request.setpOCode(poCode);
        request.setPostmanCode(postmanCode);
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setStatusCode(statusCode);
        request.setRouteCode(routeCode);
        mInteractor.getCancelDeliveryStatic(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        listStatistic -> {
                            if (listStatistic.getErrorCode().equals("00")) {
                                mView.showListSuccess(listStatistic.getStatisticItemList());
                                mView.showSuccessToast(listStatistic.getMessage());
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
}

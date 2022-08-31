package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13TabContract;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;
import com.ems.dingdong.model.response.CancelStatisticItem;
import com.ems.dingdong.network.NetWorkController;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CancelBD13StatisticPresenter extends Presenter<CancelBD13StatisticContract.View,
        CancelBD13StatisticContract.Interactor> implements CancelBD13StatisticContract.Presenter {

    private CancelBD13TabContract.OnTabListener tabListener;
    private Map<String, List<CancelStatisticItem>> map;

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
                        result -> {
                            if (result.getErrorCode().equals("00")) {
//                                List<CancelStatisticItem> statisticItemList = NetWorkController.getGson().fromJson(result.getData(),new TypeToken<List<CancelStatisticItem>>(){}.getType());
                                map = groupByCancelStatisticMap(result.getStatisticItemList());
                                mView.showListSuccess(groupByCancelStatisticList(map));
                            } else {
                                mView.showError(result.getMessage());
                            }
                        }
                        ,
                        error -> mView.showError(error.getMessage())
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

    @Override
    public List<CancelStatisticItem> getListFromMap(String ladingCode) {
        return map.get(ladingCode);
    }

    @Override
    public int getCurrentTab() {
        return tabListener.getCurrentTab();
    }

    private Map<String, List<CancelStatisticItem>> groupByCancelStatisticMap(List<CancelStatisticItem> listCancelStatistic) {
        Map<String, List<CancelStatisticItem>> listMap = new HashMap<>();
        for (CancelStatisticItem item : listCancelStatistic) {
            String ladingCode = item.getLadingCode();
            if (listMap.containsKey(ladingCode)) {
                List<CancelStatisticItem> list = listMap.get(ladingCode);
                if (list != null) {
                    list.add(item);
                }
            } else {
                List<CancelStatisticItem> list = new ArrayList<>();
                list.add(item);
                listMap.put(ladingCode, list);
            }
        }
        return listMap;
    }

    private List<CancelStatisticItem> groupByCancelStatisticList(Map<String, List<CancelStatisticItem>> map) {
        List<CancelStatisticItem> resultsList = new ArrayList<>();
        for (Map.Entry<String, List<CancelStatisticItem>> entry : map.entrySet()) {
            resultsList.add(entry.getValue().get(0));
        }
        return resultsList;
    }
}

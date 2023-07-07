package com.ems.dingdong.functions.mainhome.phathang.new_noptien.huynop;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class HuyNopInteractor extends Interactor<HuyNopContract.Presenter> implements HuyNopContract.Interactor {
    public HuyNopInteractor(HuyNopContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment) {
        return NetWorkControllerGateWay.getDataSuccess(dataRequestPayment);
    }

    @Override
    public Single<SimpleResult> cancelPayment(DataRequestPayment dataRequestPayment) {
        return NetWorkControllerGateWay.cancelPayment(dataRequestPayment);
    }
}

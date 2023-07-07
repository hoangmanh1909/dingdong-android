package com.ems.dingdong.functions.mainhome.phathang.new_noptien.history;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class HistoryInteractor extends Interactor<HistoryContract.Presenter> implements HistoryContract.Interactor {
    public HistoryInteractor(HistoryContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment) {
        return NetWorkControllerGateWay.getHistoryPayment(dataRequestPayment);
    }
}

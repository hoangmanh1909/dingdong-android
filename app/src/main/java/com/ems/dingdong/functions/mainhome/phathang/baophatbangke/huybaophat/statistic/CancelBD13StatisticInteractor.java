package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.CancelDeliveryResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Observable;

public class CancelBD13StatisticInteractor extends Interactor<CancelBD13StatisticContract.Presenter> implements CancelBD13StatisticContract.Interactor {

    CancelBD13StatisticInteractor(CancelBD13StatisticContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Observable<SimpleResult> getCancelDeliveryStatic(CancelDeliveryStatisticRequest request) {
        return NetWorkControllerGateWay.cancelDeliveryStatistic(request);
    }
}

package com.ems.dingdong.functions.mainhome.phathang.thongke.sml;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataHistoryResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.StatisticSMLDeliveryFailRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class SmartlockStatisticInteractor extends Interactor<SmartlockStatisticContract.Presenter>
        implements SmartlockStatisticContract.Interactor {

    SmartlockStatisticInteractor(SmartlockStatisticContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> statisticSMLDeliveryFail(StatisticSMLDeliveryFailRequest request) {
        return NetWorkControllerGateWay.statisticSMLDeliveryFail(request);
    }
}


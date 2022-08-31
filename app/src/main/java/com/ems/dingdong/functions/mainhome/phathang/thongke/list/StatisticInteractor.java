package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

/**
 * The Statistic interactor
 */
class StatisticInteractor extends Interactor<StatisticContract.Presenter>
        implements StatisticContract.Interactor {

    StatisticInteractor(StatisticContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchDeliveryStatistic(String fromDate, String toDate, String status, String postmanId, String routeCode, CommonCallback<CommonObjectListResult> callback) {
        NetWorkControllerGateWay.searchDeliveryStatistic(fromDate, toDate, status, postmanId, routeCode, callback);
    }
}

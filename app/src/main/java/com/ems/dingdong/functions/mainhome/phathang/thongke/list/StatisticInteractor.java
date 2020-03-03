package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The Statistic interactor
 */
class StatisticInteractor extends Interactor<StatisticContract.Presenter>
        implements StatisticContract.Interactor {

    StatisticInteractor(StatisticContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchDeliveryStatistic(String fromDate, String toDate, String status, String postmanId, String shift, String routeCode, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchDeliveryStatistic(fromDate, toDate, status, postmanId, shift, routeCode, callback);
    }
}

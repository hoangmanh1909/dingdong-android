package com.vinatti.dingdong.functions.mainhome.phathang.thongke.list;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The Statistic interactor
 */
class StatisticInteractor extends Interactor<StatisticContract.Presenter>
        implements StatisticContract.Interactor {

    StatisticInteractor(StatisticContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchDeliveryStatistic(String fromDate, String status, String postmanId, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchDeliveryStatistic(fromDate,status,postmanId,callback);
    }
}

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
    public void searchDeliveryStatistic(String fromDate, String status, String postmanId, String shift, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchDeliveryStatistic(fromDate,status,postmanId,shift,callback);
    }
}

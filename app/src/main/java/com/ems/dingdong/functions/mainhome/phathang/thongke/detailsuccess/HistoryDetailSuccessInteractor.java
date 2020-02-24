package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The History interactor
 */
class HistoryDetailSuccessInteractor extends Interactor<HistoryDetailSuccessContract.Presenter>
        implements HistoryDetailSuccessContract.Interactor {

    HistoryDetailSuccessInteractor(HistoryDetailSuccessContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void statisticDeliveryGeneral(String postmanID, String fromDate, String toDate, boolean isSuccess, String routeCode, CommonCallback<StatisticDeliveryGeneralResult> callback) {
        NetWorkController.statisticDeliveryGeneral(postmanID, fromDate, toDate,isSuccess, routeCode, callback);
    }
}

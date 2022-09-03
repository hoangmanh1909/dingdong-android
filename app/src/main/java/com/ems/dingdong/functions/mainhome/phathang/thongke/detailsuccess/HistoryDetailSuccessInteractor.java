package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Constants;

/**
 * The History interactor
 */
class HistoryDetailSuccessInteractor extends Interactor<HistoryDetailSuccessContract.Presenter>
        implements HistoryDetailSuccessContract.Interactor {

    HistoryDetailSuccessInteractor(HistoryDetailSuccessContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void statisticDeliveryGeneral(String postmanID, String fromDate, String toDate, StatisticType statisticType, String routeCode, CommonCallback<StatisticDeliveryGeneralResult> callback) {

        switch (statisticType) {
            case CONTINUOUS_DELIVERY:
                NetWorkControllerGateWay.getLadingStatusGeneral(postmanID, fromDate, toDate, Constants.CONTINUOUS_DELIVERY_CODE, routeCode, callback);
                break;
            case SUCCESS_DELIVERY:
                NetWorkControllerGateWay.statisticDeliveryGeneral(postmanID, fromDate, toDate, true, routeCode, callback);
                break;

            case RETURN_DELIVERY:
                NetWorkControllerGateWay.getLadingStatusGeneral(postmanID, fromDate, toDate, Constants.RETURNED_DELIVERY_CODE, routeCode, callback);
                break;

            case ERROR_DELIVERY:
                NetWorkControllerGateWay.statisticDeliveryGeneral(postmanID, fromDate, toDate, false, routeCode, callback);
                break;
        }
    }
}

package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.utiles.Constants;

/**
 * The CommonObject interactor
 */
class ListDeliverySuccessDetailInteractor extends Interactor<ListDeliverySuccessDetailContract.Presenter>
        implements ListDeliverySuccessDetailContract.Interactor {

    ListDeliverySuccessDetailInteractor(ListDeliverySuccessDetailContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void statisticDeliveryDetail(String serviceCode, int typeDelivery, String postmanID,
                                        String fromDate, String toDate, StatisticType statisticType,
                                        String routeCode, CommonCallback<SimpleResult> callback) {
        switch (statisticType) {
            case CONTINUOUS_DELIVERY:
                NetWorkControllerGateWay.getLadingStatusDetail(typeDelivery, serviceCode, postmanID, fromDate, toDate, Constants.CONTINUOUS_DELIVERY_CODE, routeCode, callback);
                break;

            case SUCCESS_DELIVERY:
                NetWorkControllerGateWay.statisticDeliveryDetail(serviceCode, typeDelivery, postmanID, fromDate, toDate, true, routeCode, callback);
                break;

            case RETURN_DELIVERY:
                NetWorkControllerGateWay.getLadingStatusDetail(typeDelivery, serviceCode, postmanID, fromDate, toDate, Constants.RETURNED_DELIVERY_CODE, routeCode, callback);
                break;

            case ERROR_DELIVERY:
                NetWorkControllerGateWay.statisticDeliveryDetail(serviceCode, typeDelivery, postmanID, fromDate, toDate, false, routeCode, callback);
                break;
        }
    }
}

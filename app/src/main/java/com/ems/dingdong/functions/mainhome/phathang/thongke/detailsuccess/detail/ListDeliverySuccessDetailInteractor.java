package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The CommonObject interactor
 */
class ListDeliverySuccessDetailInteractor extends Interactor<ListDeliverySuccessDetailContract.Presenter>
        implements ListDeliverySuccessDetailContract.Interactor {

    ListDeliverySuccessDetailInteractor(ListDeliverySuccessDetailContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void statisticDeliveryDetail(String serviceCode, int typeDelivery, CommonCallback<StatisticDeliveryDetailResult> callback) {
        NetWorkController.statisticDeliveryDetail(serviceCode, typeDelivery, callback);
    }
}

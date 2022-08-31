package com.ems.dingdong.functions.mainhome.phathang.gachno.searchlist;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

/**
 * The ListGachNo interactor
 */
class ListGachNoInteractor extends Interactor<ListGachNoContract.Presenter>
        implements ListGachNoContract.Interactor {

    ListGachNoInteractor(ListGachNoContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void deliveryGetPaypostError(String fromDate, String toDate, CommonCallback<GachNoResult> callback) {
        NetWorkControllerGateWay.deliveryGetPaypostError(fromDate, toDate,callback);
    }

    @Override
    public void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.paymentPaypost(request ,callback);
    }

}

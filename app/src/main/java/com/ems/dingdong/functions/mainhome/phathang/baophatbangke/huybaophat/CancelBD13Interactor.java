package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;
import com.ems.dingdong.network.NetWorkController;

import java.util.List;

public class CancelBD13Interactor extends Interactor<CancelBD13Contract.Presenter> implements CancelBD13Contract.Interactor {
    public CancelBD13Interactor(CancelBD13Contract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode, CommonCallback<DingDongGetCancelDeliveryResponse> commonCallback) {
        NetWorkController.getCancelDelivery(postmanCode,routeCode,fromDate,toDate,ladingCode,commonCallback);
    }

    @Override
    public void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList, CommonCallback<SimpleResult> callback) {
        NetWorkController.cancelDelivery(dingDongGetCancelDeliveryRequestList,callback);
    }
}

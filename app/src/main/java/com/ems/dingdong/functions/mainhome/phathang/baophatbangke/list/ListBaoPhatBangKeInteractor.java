package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;

/**
 * The CommonObject interactor
 */
class ListBaoPhatBangKeInteractor extends Interactor<ListBaoPhatBangKeContract.Presenter>
        implements ListBaoPhatBangKeContract.Interactor {

    ListBaoPhatBangKeInteractor(ListBaoPhatBangKeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public void searchDeliveryPostman(String postmanID, String fromDate, String shiftID, String chuyenthu, String tuiso, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchDeliveryPostman(postmanID, fromDate, shiftID, chuyenthu, tuiso, callback);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }


    @Override
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.paymentDelivery(request, callback);
    }

    @Override
    public void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(request, callback);
    }
}

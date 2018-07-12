package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

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
    public void searchDeliveryPostman(String postmanID, String fromDate, String shiftID, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.searchDeliveryPostman(postmanID, fromDate, shiftID,callback);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }

    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String sign, String note, String amount, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, sign,note, amount, commonCallback);
    }
}

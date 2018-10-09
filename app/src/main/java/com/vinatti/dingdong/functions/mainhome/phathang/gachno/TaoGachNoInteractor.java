package com.vinatti.dingdong.functions.mainhome.phathang.gachno;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The TaoGachNo interactor
 */
class TaoGachNoInteractor extends Interactor<TaoGachNoContract.Presenter>
        implements TaoGachNoContract.Interactor {

    TaoGachNoInteractor(TaoGachNoContract.Presenter presenter) {
        super(presenter);
    }
    @Override
    public void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback) {
        NetWorkController.searchParcelCodeDelivery(parcelCode, callback);
    }
    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                callback);
    }

    @Override
    public void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String amount, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentPaypost(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note,amount, commonCallback);
    }
}

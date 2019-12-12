package com.ems.dingdong.functions.mainhome.phathang.sign;


import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The SignDraw interactor
 */
class SignDrawInteractor extends Interactor<SignDrawContract.Presenter>
        implements SignDrawContract.Interactor {

    SignDrawInteractor(SignDrawContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String amount, String signatureCapture, String ladingPostmanID, String routeCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, signatureCapture, "",amount,ladingPostmanID,routeCode, callback);
    }

    @Override
    public void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String amount, String routeCode, String ladingPostmanID, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note,amount,routeCode, ladingPostmanID, commonCallback);
    }
}

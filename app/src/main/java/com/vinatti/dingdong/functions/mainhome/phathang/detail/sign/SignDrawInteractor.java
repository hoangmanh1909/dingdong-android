package com.vinatti.dingdong.functions.mainhome.phathang.detail.sign;


import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The SignDraw interactor
 */
class SignDrawInteractor extends Interactor<SignDrawContract.Presenter>
        implements SignDrawContract.Interactor {

    SignDrawInteractor(SignDrawContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, signatureCapture, callback);
    }
}

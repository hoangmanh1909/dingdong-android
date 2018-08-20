package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The BaoPhatBangKeDetail interactor
 */
class BaoPhatBangKeDetailInteractor extends Interactor<BaoPhatBangKeDetailContract.Presenter>
        implements BaoPhatBangKeDetailContract.Interactor {

    BaoPhatBangKeDetailInteractor(BaoPhatBangKeDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }
    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status,
                                  String paymentChannel, String deliveryType, String sign, String note, String collectAmount,String ladingPostmanID , CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                solutionCode, status, paymentChannel, deliveryType, sign,note, collectAmount,ladingPostmanID, commonCallback);
    }
    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                callback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkController.getSolutionByReasonCode(code,commonCallback);
    }
    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate,
                                  String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status, String paymentChannel,
                                  String deliveryType, String collectAmount, String signatureCapture,String ladingPostmanID , CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, signatureCapture, "", collectAmount,ladingPostmanID, callback);
    }

    @Override
    public void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String collectAmount, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note,collectAmount, commonCallback);
    }
}

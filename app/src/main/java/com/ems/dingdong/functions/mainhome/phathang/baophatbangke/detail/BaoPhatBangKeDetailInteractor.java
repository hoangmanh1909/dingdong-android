package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.SupportRequest;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.network.NetWorkController;

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
                                  String paymentChannel, String deliveryType, String sign, String note, String collectAmount, String ladingPostmanID, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                solutionCode, status, paymentChannel, deliveryType, sign, note, collectAmount, ladingPostmanID, "", commonCallback);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, callback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkController.getSolutionByReasonCode(code, commonCallback);
    }

    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate,
                                  String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status, String paymentChannel,
                                  String deliveryType, String note, String collectAmount, String signatureCapture, String ladingPostmanID, String fileNames, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, signatureCapture, "", collectAmount, ladingPostmanID, fileNames, callback);
    }

    @Override
    public void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String collectAmount, String fileNames, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, collectAmount, fileNames, commonCallback);
    }

    @Override
    public void getInquiryAmount(String parcelCode, CommonCallback<InquiryAmountResult> callback) {
        NetWorkController.getInquiryAmount(parcelCode, callback);
    }

    @Override
    public void updateMobile(String code, String mobileNumber, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.updateMobile(code, mobileNumber, commonCallback);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }

    @Override
    public void addSupportType(SupportRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.addSupport(request, callback);
    }
}

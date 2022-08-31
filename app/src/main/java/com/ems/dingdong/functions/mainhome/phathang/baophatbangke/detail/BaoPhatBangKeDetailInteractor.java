package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

/**
 * The BaoPhatBangKeDetail interactor
 */
class BaoPhatBangKeDetailInteractor extends Interactor<BaoPhatBangKeDetailContract.Presenter>
        implements BaoPhatBangKeDetailContract.Interactor {

    BaoPhatBangKeDetailInteractor(BaoPhatBangKeDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasons(commonCallback);
    }


    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkControllerGateWay.getSolutionByReasonCode(code, commonCallback);
    }


    @Override
    public void getInquiryAmount(String parcelCode, CommonCallback<InquiryAmountResult> callback) {
        NetWorkControllerGateWay.getInquiryAmount(parcelCode, callback);
    }

    @Override
    public void updateMobile(String code, String type, String mobileNumber, CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.updateMobile(code, type, mobileNumber, commonCallback);
    }

    @Override
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.paymentDelivery(request, callback);
    }

    @Override
    public void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.pushToPNSDelivery(request, callback);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }
}

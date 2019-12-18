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
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, callback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkController.getSolutionByReasonCode(code, commonCallback);
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
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.paymentDelivery(request, callback);
    }

    @Override
    public void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(request, callback);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }
}

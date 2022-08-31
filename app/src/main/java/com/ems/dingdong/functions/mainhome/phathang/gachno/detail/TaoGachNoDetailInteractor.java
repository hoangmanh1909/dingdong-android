package com.ems.dingdong.functions.mainhome.phathang.gachno.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

/**
 * The BaoPhatBangKeDetail interactor
 */
class TaoGachNoDetailInteractor extends Interactor<TaoGachNoDetailContract.Presenter>
        implements TaoGachNoDetailContract.Interactor {

    TaoGachNoDetailInteractor(TaoGachNoDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasons(commonCallback);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber, PostmanId, POCode,
                ladingCode, callback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkControllerGateWay.getSolutionByReasonCode(code, commonCallback);
    }

    @Override
    public void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.paymentPaypost(request, callback);
    }

}

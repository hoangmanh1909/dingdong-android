package com.ems.dingdong.functions.mainhome.phathang.sign;


import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

/**
 * The SignDraw interactor
 */
class SignDrawInteractor extends Interactor<SignDrawContract.Presenter>
        implements SignDrawContract.Interactor {

    SignDrawInteractor(SignDrawContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.paymentDelivery(request, callback);
    }

    @Override
    public void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.pushToPNSDelivery(request, callback);
    }

}

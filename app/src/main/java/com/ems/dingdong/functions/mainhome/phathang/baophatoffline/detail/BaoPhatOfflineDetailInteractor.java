package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;

/**
 * The BaoPhatBangKeDetail interactor
 */
class BaoPhatOfflineDetailInteractor extends Interactor<BaoPhatOfflineDetailContract.Presenter>
        implements BaoPhatOfflineDetailContract.Interactor {

    BaoPhatOfflineDetailInteractor(BaoPhatOfflineDetailContract.Presenter presenter) {
        super(presenter);
    }



    @Override
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.paymentDelivery(request, callback);
    }

    @Override
    public void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(request, callback);
    }
}

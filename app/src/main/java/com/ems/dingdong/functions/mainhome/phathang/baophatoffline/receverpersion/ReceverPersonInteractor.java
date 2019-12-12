package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.receverpersion;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.network.NetWorkController;

/**
 * The ReceverPerson interactor
 */
class ReceverPersonInteractor extends Interactor<ReceverPersonContract.Presenter>
        implements ReceverPersonContract.Interactor {

    ReceverPersonInteractor(ReceverPersonContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.paymentDelivery(request, callback);
    }
}

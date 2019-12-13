package com.ems.dingdong.functions.mainhome.phathang.gachno.receverpersion;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
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
    public void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.paymentPaypost(request, callback);
    }
}

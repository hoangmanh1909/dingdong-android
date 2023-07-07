package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.otp;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class OtpInteractor extends Interactor<OtpContract.Presenter> implements OtpContract.Interactor {
    public OtpInteractor(OtpContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel) {
        return NetWorkControllerGateWay.confirmPayment(paymentConfirmModel);
    }
}

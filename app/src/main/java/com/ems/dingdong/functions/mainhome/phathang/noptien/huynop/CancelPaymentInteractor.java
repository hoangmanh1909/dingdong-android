package com.ems.dingdong.functions.mainhome.phathang.noptien.huynop;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataHistoryResult;
import com.ems.dingdong.model.EWalletRequestResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class CancelPaymentInteractor extends Interactor<CancelPaymentContract.Presenter> implements CancelPaymentContract.Interactor {

    public CancelPaymentInteractor(CancelPaymentContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<EWalletDataHistoryResult> getHistoryPayment(DataRequestPayment dataRequestPayment) {
        return NetWorkController.getHistoryPayment(dataRequestPayment);
    }


    @Override
    public Single<EWalletRequestResult> requestPayment(PaymentRequestModel paymentRequestModel) {
        return NetWorkController.requestPayment(paymentRequestModel);
    }

    @Override
    public Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel) {
        return NetWorkController.confirmPayment(paymentConfirmModel);
    }

    @Override
    public Single<SimpleResult> cancelPayment(DataRequestPayment dataRequestPayment) {
        return NetWorkController.deletePayment(dataRequestPayment);
    }
}

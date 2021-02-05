package com.ems.dingdong.functions.mainhome.phathang.noptien;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataHistoryResult;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.EWalletRemoveDataRequest;
import com.ems.dingdong.model.EWalletRequestResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class PaymentInteractor extends Interactor<PaymentContract.Presenter> implements PaymentContract.Interactor {

    public PaymentInteractor(PaymentContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<EWalletDataResult> getDataPayment(String fromDate, String toDate, String poCode, String routeCode, String postmanCode) {
        return NetWorkController.getDataPayment(fromDate, toDate, poCode, routeCode, postmanCode);
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
    public Single<SimpleResult> deletePayment(DataRequestPayment dataRequestPayment) {
        return NetWorkController.deletePayment(dataRequestPayment);
    }

    @Override
    public Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel) {
        return NetWorkController.confirmPayment(paymentConfirmModel);
    }
}

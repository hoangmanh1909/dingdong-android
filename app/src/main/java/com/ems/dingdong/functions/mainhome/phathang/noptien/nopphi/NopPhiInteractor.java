package com.ems.dingdong.functions.mainhome.phathang.noptien.nopphi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.EWalletRequestResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class NopPhiInteractor extends Interactor<NopPhiContract.Presenter> implements NopPhiContract.Interactor {

    public NopPhiInteractor(NopPhiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<EWalletDataResult> getDataPayment(String fromDate, String toDate, String poCode, String routeCode, String postmanCode) {
        return null;
    }

    @Override
    public Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment) {
        return NetWorkController.getHistoryPayment(dataRequestPayment);
    }

    @Override
    public Single<SimpleResult> requestPayment(PaymentRequestModel paymentRequestModel) {
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

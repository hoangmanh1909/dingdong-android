package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.PaymentRequestResponse;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

public class NopTienInteractor extends Interactor<NopTienContract.Presenter> implements NopTienContract.Interactor {
    public NopTienInteractor(NopTienContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<EWalletDataResult> getDataPayment(String serviceCode, String fromDate, String toDate, String poCode, String routeCode, String postmanCode) {
        return NetWorkControllerGateWay.getDataPayment(serviceCode, fromDate, toDate, poCode, routeCode, postmanCode);
    }



    @Override
    public Single<SimpleResult> getDanhSachNganHang() {
        return NetWorkControllerGateWay.getDanhSachNganHang();
    }

    @Override
    public Single<SimpleResult> getDDsmartBankConfirmLinkRequest(BaseRequestModel request) {
        return NetWorkControllerGateWay.getDDsmartBankConfirmLinkRequest(request);
    }

    @Override
    public Single<PaymentRequestResponse> requestPayment(PaymentRequestModel paymentRequestModel) {
        return NetWorkControllerGateWay.requestPayment(paymentRequestModel);
    }

    @Override
    public Single<SimpleResult> deletePayment(DataRequestPayment dataRequestPayment) {
        return NetWorkControllerGateWay.deletePayment(dataRequestPayment);
    }
}

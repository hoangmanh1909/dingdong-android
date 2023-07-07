package com.ems.dingdong.functions.mainhome.phathang.new_noptien.history;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.PaymentRequestResponse;

import java.util.List;

import io.reactivex.Single;

public class HistoryContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<EWalletDataResponse> eWalletDataResponses);

        void showConfirmError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getHistoryPayment(DataRequestPayment dataRequestPayment, int type);

        void showBarcode(BarCodeCallback barCodeCallback);
    }
}

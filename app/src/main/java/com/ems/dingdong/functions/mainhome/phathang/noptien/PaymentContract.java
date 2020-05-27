package com.ems.dingdong.functions.mainhome.phathang.noptien;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.EWalletRequestResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;

import java.util.List;

import io.reactivex.Single;

public interface PaymentContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<EWalletDataResult> getDataPayment(String fromDate, String toDate, String poCode,
                                                 String routeCode, String postmanCode);

        Single<EWalletRequestResult> requestPayment(PaymentRequestModel paymentRequestModel);

        Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<EWalletDataResponse> eWalletDataResponses);

        void showRequestSuccess(String message, String requestId, String retRefNumber);

        void showConfirmSuccess(String message);

        void showConfirmError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showLinkWalletFragment();

        void getDataPayment(String poCode, String routeCode, String postmanCode, String fromDate, String toDate);

        void requestPayment(List<EWalletDataResponse> list, String poCode, String routeCode, String postmanCode);

        void confirmPayment(String otp, String requestId, String retRefNumber, String poCode, String routeCode, String postmanCode);
    }
}

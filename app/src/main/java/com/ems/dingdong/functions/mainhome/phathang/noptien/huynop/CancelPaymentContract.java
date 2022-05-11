package com.ems.dingdong.functions.mainhome.phathang.noptien.huynop;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataHistoryResult;
import com.ems.dingdong.model.EWalletRequestResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;

import java.util.List;

import io.reactivex.Single;

public interface CancelPaymentContract {
    interface Interactor extends IInteractor<Presenter> {

        Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment);

        Single<SimpleResult> requestPayment(PaymentRequestModel paymentRequestModel);

        Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel);

        Single<SimpleResult> cancelPayment(DataRequestPayment dataRequestPayment);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<EWalletDataResponse> eWalletDataResponses);

        void showRequestSuccess(String message, String requestId, String retRefNumber);

        void showConfirmSuccess(String message);

        void showConfirmError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showBarcode(BarCodeCallback barCodeCallback);

        void showLinkWalletFragment();

        void cancelPayment(List<EWalletDataResponse> list,int type,String lydo);

        int getPositionTab();

        void getHistoryPayment(DataRequestPayment dataRequestPayment,int type);

        ContainerView getContainerView();

        void titleChanged(int quantity, int currentSetTab);

        int getCurrentTab();

        void onCanceled();

        void showLienket();

    }
    interface OnTabListener {
        /**
         * Event when tab cancel delivery success.
         */
        void onCanceledDelivery();

        /**
         * Event when title change.
         */
        void onQuantityChange(int quantity, int currentSetTab);

        int getCurrentTab();
    }
}

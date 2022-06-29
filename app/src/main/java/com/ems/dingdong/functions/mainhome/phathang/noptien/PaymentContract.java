package com.ems.dingdong.functions.mainhome.phathang.noptien;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataHistoryResult;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.EWalletRemoveDataRequest;
import com.ems.dingdong.model.EWalletRemoveRequest;
import com.ems.dingdong.model.EWalletRequestResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.SmartBankLink;

import java.util.List;

import io.reactivex.Single;

public interface PaymentContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<EWalletDataResult> getDataPayment(String serviceCode, String fromDate, String toDate, String poCode,
                                                 String routeCode, String postmanCode);

        Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment);

        Single<EWalletRequestResult> requestPayment(PaymentRequestModel paymentRequestModel);

        Single<SimpleResult> deletePayment(DataRequestPayment dataRequestPayment);

        Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel);

        Single<SimpleResult> getDDsmartBankConfirmLinkRequest(BaseRequestModel request);


    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<EWalletDataResponse> eWalletDataResponses);

        void showRequestSuccess(List<LadingPaymentInfo> list, String message, String requestId, String retRefNumber);

        void showPaymenConfirmSuccess(String message);

        void showConfirmSuccess(String message);

        void showConfirmError(String message);

        void stopRefresh();

        void dongdialog();

        void showThanhCong();

        void setsmartBankConfirmLink(String x);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getDDsmartBankConfirmLinkRequest(BaseRequestModel x);

        void showBarcode(BarCodeCallback barCodeCallback);

        void showLinkWalletFragment();

        void getDataPayment(String serviceCode, String poCode, String routeCode, String postmanCode, String fromDate, String toDate);

        void requestPayment(List<LadingPaymentInfo> list, String poCode,
                            String routeCode, String postmanCode, int type,
                            String bankcode, String posmanTel, String token, SmartBankLink item);

        void deletePayment(List<EWalletDataResponse> list);

        void confirmPayment(List<LadingPaymentInfo> list, String otp, String requestId, String retRefNumber, String poCode,
                            String routeCode, String postmanCode, String mobileNumber, String token);

        int getPositionTab();

        void showLienket();

        ContainerView getContainerView();

        /**
         * Get cancel delivery record.
         *
         * @param postmanCode postman code from UserInfo
         * @param routeCode   route code from RouteInfo
         * @param fromDate    from date.
         * @param toDate      to date
         * @param ladingCode  lading code.
         */
        /**
         * cancel deliver.
         *
         * @param dingDongGetCancelDeliveryRequestList list cancel delivery chosen.
         */
        /**
         * Event refresh nearby tab.
         */
        void onCanceled();

        void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList);

        /**
         * Event set title count.
         */
        void titleChanged(int quantity, int currentSetTab);

        int getCurrentTab();

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

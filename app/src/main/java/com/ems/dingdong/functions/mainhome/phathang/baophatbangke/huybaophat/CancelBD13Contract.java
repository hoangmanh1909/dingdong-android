package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DingDongGetCancelDelivery;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;

import java.util.ArrayList;

public interface CancelBD13Contract {
    interface Interactor extends IInteractor<Presenter> {
        /**
         * Get cancel delivery record.
         *
         * @param postmanCode postman code from UserInfo
         * @param routeCode   route code from RouteInfo
         * @param fromDate    from date.
         * @param toDate      to date
         * @param ladingCode  lading code.
         */
        void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode, CommonCallback<DingDongGetCancelDeliveryResponse> commonCallback);

        /**
         * cancel deliver.
         *
         * @param dingDongGetCancelDeliveryRequestList list cancel delivery chosen.
         */
        void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        /**
         * Show list cancel delivery.
         */
        void showListSuccess(ArrayList<DingDongGetCancelDelivery> list);

        /**
         * Show error message from server.
         */
        void showError(String message);

        /**
         * Show response message from server.
         */
        void showView(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        /**
         * Show barcode scan screen.
         */
        void showBarcode(BarCodeCallback barCodeCallback);

        /**
         * Get cancel delivery record.
         *
         * @param postmanCode postman code from UserInfo
         * @param routeCode   route code from RouteInfo
         * @param fromDate    from date.
         * @param toDate      to date
         * @param ladingCode  lading code.
         */
        void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode);

        /**
         * cancel deliver.
         *
         * @param dingDongGetCancelDeliveryRequestList list cancel delivery chosen.
         */
        void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList);

        /**
         * Event refresh nearby tab.
         */
        void onCanceled();

        /**
         * Event set title count.
         */
        void titleChanged(int quantity, int currentSetTab);

        int getCurrentTab();
    }
}

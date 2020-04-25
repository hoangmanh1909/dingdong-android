package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * The CommonObject Contract
 */
interface ListBaoPhatBangKeContract {

    interface Interactor extends IInteractor<Presenter> {

        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate, CommonCallback<CommonObjectListResult> callback);

        /**
         * This search record to display and deliver.
         *
         * @param postmanID  this field is postman id, it can be collect from UserInfo that have been save in share preference.
         * @param fromDate   from date, format yyyyMMDD.
         * @param toDate     to date, format yyyyMMDD.
         * @param routeCode  route code, it can be collect from RouteInfo that have been save in share preference.
         * @param searchType type record
         *                   0 - All
         *                   1 - COD
         *                   2 - NORMAL
         *                   3 - HCC
         * @param callback   Callback retrofit.
         */
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String toDate,
                                   String routeCode,
                                   Integer searchType,
                                   CommonCallback<DeliveryPostmanResponse> callback);

        /**
         * Get all reasons when deliver not successfully.
         *
         * @param commonCallback callback retrofit.
         */
        void getReasons(CommonCallback<ReasonResult> commonCallback);


        /**
         * Deliver success.
         *
         * @param request  all param needed.
         * @param callback callback retrofit.
         */
        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback);

        /**
         * Deliver not success.
         *
         * @param request  all param needed.
         * @param callback callback retrofit.
         */
        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);

        /**
         * Call to service center to connect to calleenumber.
         *
         * @param callerNumber  caller number.
         * @param calleeNumber  callee number.
         * @param hotlineNumber hotline.
         * @param ladingCode    lading code.
         */
        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);

        /**
         * Update mobile of current lading code.
         *
         * @param code                       lading code.
         * @param phone                      new phone number.
         * @param simpleResultCommonCallback callback from retrofit.
         */
        void updateMobile(String code, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);

    }

    interface View extends PresentView<Presenter> {

        /**
         * Show response list when get success all record
         *
         * @param list returned list from server.
         * @see Interactor
         */
        void showResponseSuccess(ArrayList<DeliveryPostman> list);

        /**
         * Show response list when get success all record
         *
         * @param list returned list from server.
         * @see Interactor
         */
        void showListSuccess(List<DeliveryPostman> list);

        /**
         * Show error when error happen.
         *
         * @param message error message from server.
         * @see Interactor
         */
        void showError(String message);

        /**
         * Show error when call to service center.
         *
         * @param message error message from server.
         */
        void showCallError(String message);

        /**
         * Save reasons when get successful.
         *
         * @param reasonInfos all reasons from server.
         */
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        /**
         * Show success message.
         */
        void showSuccessMessage(String message);

        void showCallSuccess();

        void showResponseSuccessEmpty();

        void showSuccessUpdateMobile(String phone, String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        /*void searchStatisticCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate);*/

        /**
         * This search record to display and deliver.
         *
         * @param postmanID    this field is postman id, it can be collect from UserInfo that have been save in share preference.
         * @param fromDate     from date, format yyyyMMDD.
         * @param toDate       to date, format yyyyMMDD.
         * @param routeCode    route code, it can be collect from RouteInfo that have been save in share preference.
         * @param deliveryType type record
         *                     0 - All
         *                     1 - COD
         *                     2 - NORMAL
         *                     3 - HCC
         */
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String toDate,
                                   String routeCode,
                                   Integer deliveryType
        );

        /**
         * Add view XacNhanBaoPhatFragment to decide to deliver.
         *
         * @param commonObject list record chosen.
         */
        void showConfirmDelivery(List<DeliveryPostman> commonObject);

        /**
         * Set type tab.
         *
         * @param type 10 - NOT_YET_DELIVERY_TAB, 20 NOT_SUCCESSFULLY_DELIVERY_TAB
         * @return this presenter
         */
        ListBaoPhatBangKePresenter setType(int type);

        /**
         * Get all reasons from server.
         */
        void getReasons();

        /**
         * Get type tab;
         *
         * @return 10 or 20.
         */
        int getType();

        /**
         * Get lading code when fragment is added from notification, to point to exactly position when notification come.
         *
         * @return lading code.
         */
        String getLadingCode();

        /**
         * Get delivery type when fragment is added from main fragment.
         *
         * @return COD, NORMAL, HCC, ALL
         */
        int getDeliverType();

        /**
         * Set total record to tabtitle
         *
         * @param quantity quantity.
         */
        void setTitleTab(int quantity);

        /**
         * Change tab to point to exactly position of record when server send notification.
         */
        void onTabChange();

        /**
         * Synchronize nearby tab when current tab search by date.
         */
        void onSearched(String fromDate, String toDate, int currentPosition);

        /**
         * Synchronize list when deliver success or not.
         *
         * @return event when deliver success or not success.
         */
        ListDeliveryConstract.OnDeliveryNotSuccessfulChange getNotSuccessfulChange();

        /**
         * Show barcode scan screen.
         *
         * @param barCodeCallback callback when scan.
         */
        void showBarcode(BarCodeCallback barCodeCallback);

        int getPositionTab();

        /**
         * Call to service center to connect callee number.
         *
         * @param phone      callee number.
         * @param parcelCode lading code.
         */
        void callForward(String phone, String parcelCode);

        /**
         * Update callee mobile number.
         */
        void updateMobile(String phone, String parcelCode);

        /**
         * Direction on vietmap.
         *
         * @param address receiver address.
         */
        void vietmapSearch(String address);

        /**
         * Make a call by sim card.
         *
         * @param calleeNumber callee number
         */
        void callBySimCard(String calleeNumber);


        /**
         * Make a call by wifi.
         *
         * @param calleeNumber callee number
         */
        void callByWifi(String calleeNumber);
    }

}




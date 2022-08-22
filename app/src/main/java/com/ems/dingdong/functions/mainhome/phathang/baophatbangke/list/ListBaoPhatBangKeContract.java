package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.Values;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.VpostcodeModel;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.XacMinhRespone;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.model.response.VerifyAddressRespone;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

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
                                       String toAssignDate, CommonCallback<SimpleResult> callback);

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
        Call<SimpleResult> searchDeliveryPostman(String postmanID,
                                                            String fromDate,
                                                            String toDate,
                                                            String routeCode,
                                                            Integer searchType,
                                                            CommonCallback<SimpleResult> callback);

        /**
         * Call to service center to connect to calleenumber.
         *
         * @param callerNumber  caller number.
         * @param calleeNumber  callee number.
         * @param hotlineNumber hotline.
         * @param ladingCode    lading code.
         */
        Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                 String callForwardType, String hotlineNumber,
                                                 String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);

        /**
         * Update mobile of current lading code.
         *
         * @param code                       lading code.
         * @param phone                      new phone number.
         * @param simpleResultCommonCallback callback from retrofit.
         */
        Call<SimpleResult> updateMobile(String code, String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);

        Call<SimpleResult> updateMobileSender(String code, String type, String phoneSender, CommonCallback<SimpleResult> simpleResultCommonCallback);

        Single<SimpleResult> _phatSml(SMLRequest smlRequest);

        Single<SimpleResult> _huySml(SMLRequest smlRequest);

        Single<VerifyAddressRespone> ddVerifyAddress(VerifyAddress verifyAddress);

        Single<XacMinhRespone> ddCreateVietMapRequest(CreateVietMapRequest createVietMapRequest);

        Single<SimpleResult> ddSreachPhone(PhoneNumber dataRequestPayment);

        Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude);

        Single<SimpleResult> ddCall(CallLiveMode callLiveMode);
    }

    interface View extends PresentView<Presenter> {
        void showDiachi(String x);

        void showList(VpostcodeModel getListVpostV1);

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

        void showCallSuccess(String phone);

        void showSuccessUpdateMobile(String phone, String message,int type);

        void showSuccessUpdateMobileSender(String phoneSender, String message);

        void showAddress(Values x);

        void showThongBao(String mess);

        void shoSucces(String mess);

        void showCallLive(String phone);

        void phatSmlSuccess(String message);

        void huySmlSuccess(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        /*void searchStatisticCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate);*/

        void showLog(String maE);

        void ddSreachPhone(PhoneNumber dataRequestPayment);

        void getMapVitri(Double v1, Double v2);

        void _phatSml(SMLRequest smlRequest);

        void _huySml(SMLRequest smlRequest);

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
        void updateMobile(String phone, String parcelCode,int type);

        void updateMobileSender(String phoneSender, String parcelCode);

        /**
         * Direction on vietmap.
         *
         * @param vpostcodeModels receiver address.
         */
        void vietmapSearch(List<VpostcodeModel> vpostcodeModels);

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

        void callByCtellFree(String calleeNumber);

        void getDDVeryAddress(VerifyAddress verifyAddress);

        void ddCreateVietMap(CreateVietMapRequest createVietMapRequest);

        void showAddressDetail(List<VpostcodeModel> addressListModel, TravelSales ApiTravel);

        void showLoci(String mess);

        void ddCall(CallLiveMode r);
    }

}




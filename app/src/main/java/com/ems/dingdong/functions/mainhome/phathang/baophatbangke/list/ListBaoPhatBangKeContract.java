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
import com.ems.dingdong.model.XacMinhDiaChiResult;
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

        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String toDate,
                                   String shiftID,
                                   String chuyenthu,
                                   String tuiso,
                                   String routeCode,
                                   CommonCallback<DeliveryPostmanResponse> callback);

        void getReasons(CommonCallback<ReasonResult> commonCallback);


        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback);

        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);

        void updateMobile(String code, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);

    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<DeliveryPostman> list);

        void showListSuccess(List<DeliveryPostman> list, int focusedPosition);

        void showError(String message);

        void showCallError(String message);

        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        void showSuccessMessage(String message);

        void showCallSuccess();

        void showResponseSuccessEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        /*void searchStatisticCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate);*/
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String toDate,
                                   String shiftID,
                                   String chuyenthu,
                                   String tuiso,
                                   String routeCode
        );

        void showDetailView(DeliveryPostman commonObject);

        void showConfirmDelivery(List<DeliveryPostman> commonObject);

        ListBaoPhatBangKePresenter setType(int type);

        void getReasons();

        int getType();

        void submitToPNS(List<CommonObject> commonObjects, String reason, String solution, String note, String sign);

        void nextReceverPerson(List<CommonObject> commonObjects);

        void showBarcode(BarCodeCallback barCodeCallback);

        int getPositionTab();

        void callForward(String phone, String parcelCode);

        void updateMobile(String phone, String parcelCode);

        void vietmapSearch(String address);

    }
}




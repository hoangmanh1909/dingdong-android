package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;

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
                                   String shiftID, String chuyenthu, String tuiso, CommonCallback<CommonObjectListResult> callback);
        void getReasons(CommonCallback<ReasonResult> commonCallback);

        void pushToPNSDelivery(String postmanID, String ladingCode,
                               String deliveryPOCode, String deliveryDate,
                               String deliveryTime, String receiverName,
                               String reasonCode, String solutionCode, String status,
                               String paymentChannel, String deliveryType,
                               String sign, String note, String amount,String ladingPostmanID , CommonCallback<SimpleResult> commonCallback);
        void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode,
                             String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber,
                             String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType,
                             String signatureCapture, String note, String collectAmount, CommonCallback<SimpleResult> commonCallback);
        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);
        void updateMobile(String code, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);

    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showError(String message);
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        void showSuccessMessage(String message);

        void showResponseSuccessEmpty();
        void showCallSuccess();
        void showView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate);
        void searchDeliveryPostman(String postmanID,
                                   String fromDate,
                                   String shiftID, String chuyenthu, String tuiso);
        void showDetailView(CommonObject commonObject);
        ListBaoPhatBangKePresenter setType(int type);
        void getReasons();
        int getType();
        void submitToPNS(List<CommonObject> commonObjects, String reason, String solution, String note, String sign);

        void nextReceverPerson(List<CommonObject> commonObjects);

        void showBarcode(BarCodeCallback barCodeCallback);

        int getPositionTab();

        void callForward(String phone,String code);
        void updateMobile(String phone,String code);
        boolean isTheSameRouteAndOrder(List<CommonObject> commonObjectList);
    }

}




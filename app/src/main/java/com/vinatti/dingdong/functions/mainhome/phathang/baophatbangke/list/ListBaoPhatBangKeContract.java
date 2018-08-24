package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;

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
                                   String shiftID, CommonCallback<CommonObjectListResult> callback);
        void getReasons(CommonCallback<ReasonResult> commonCallback);

        void pushToPNSDelivery(String postmanID, String ladingCode,
                               String deliveryPOCode, String deliveryDate,
                               String deliveryTime, String receiverName,
                               String reasonCode, String solutionCode, String status,
                               String paymentChannel, String deliveryType,
                               String sign, String note, String amount,String ladingPostmanID , CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showError(String message);
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        void showSuccessMessage(String message);

        void showResponseSuccessEmpty();
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
                                   String shiftID);
        void showDetailView(CommonObject commonObject);
        ListBaoPhatBangKePresenter setType(int type);
        void getReasons();
        int getType();
        void submitToPNS(List<CommonObject> commonObjects, String reason, String solution, String note, String sign);

        void nextReceverPerson(List<CommonObject> commonObjects);

        void showBarcode(BarCodeCallback barCodeCallback);

        int getPositionTab();
    }
}




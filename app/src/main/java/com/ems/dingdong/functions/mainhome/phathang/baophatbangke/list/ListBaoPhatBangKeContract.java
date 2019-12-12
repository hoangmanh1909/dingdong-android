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
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

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


        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback);

        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<CommonObject> list);

        void showError(String message);
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        void showSuccessMessage(String message);

        void showResponseSuccessEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        /*void searchOrderPostmanCollect(String orderPostmanID,
                                       String orderID,
                                       String postmanID,
                                       String status,
                                       String fromAssignDate,
                                       String toAssignDate);*/
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
    }
}




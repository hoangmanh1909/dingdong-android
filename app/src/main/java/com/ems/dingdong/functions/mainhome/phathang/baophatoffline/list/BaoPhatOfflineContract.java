package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.List;

/**
 * The BaoPhatThanhCong Contract
 */
interface BaoPhatOfflineContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);

        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);

        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        // void showData(CommonObject commonObject);

        void showCallSuccess();

        void showError(String message);

        void showErrorFromRealm();

        void showSuccess(String code);

        void showListFromSearchDialog(List<CommonObject> list);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        // void searchParcelCodeDelivery(String parcelCode);

        void showDetail(CommonObject commonObject, int position);

        void pushViewConfirmAll(List<CommonObject> list);

        void callForward(String phone, String parcelCode);

        void saveLocal(CommonObject commonObject);

        void getLocalRecord(String fromDate, String toDate);

        void removeOfflineItem(String parcelCode);

        void offlineDeliver(List<CommonObject> commonObjects);
    }
}




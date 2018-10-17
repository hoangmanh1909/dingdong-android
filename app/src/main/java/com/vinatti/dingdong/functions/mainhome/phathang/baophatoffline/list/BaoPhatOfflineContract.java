package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.SimpleResult;

import java.util.List;

/**
 * The BaoPhatThanhCong Contract
 */
interface BaoPhatOfflineContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback);
        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   CommonCallback<SimpleResult> callback);
        void pushToPNSDelivery(String postmanID, String ladingCode,
                               String deliveryPOCode, String deliveryDate,
                               String deliveryTime, String receiverName,
                               String reasonCode, String solutionCode, String status,
                               String paymentChannel, String deliveryType,
                               String sign, String note, String amount,String ladingPostmanID , CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showData(CommonObject commonObject);

        void showCallSuccess();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        void searchParcelCodeDelivery(String parcelCode);

        void showDetail(CommonObject commonObject, int position);

        void pushViewConfirmAll(List<CommonObject> list);
        void callForward(String phone);

        void saveLocal(CommonObject commonObject);

        void submitToPNS(CommonObject commonObject);
    }
}




package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;

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
        void pushToPNSDelivery(String postmanID, String ladingCode,
                               String deliveryPOCode, String deliveryDate,
                               String deliveryTime, String receiverName,
                               String reasonCode, String solutionCode, String status,
                               String paymentChannel, String deliveryType,
                               String sign, String note, String amount, String ladingPostmanID, String routeCode, CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
       // void showData(CommonObject commonObject);

        void showCallSuccess();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

       // void searchParcelCodeDelivery(String parcelCode);

        void showDetail(CommonObject commonObject, int position);

        void pushViewConfirmAll(List<CommonObject> list);
        void callForward(String phone, String parcelCode);

        void saveLocal(CommonObject commonObject);

        void submitToPNS(CommonObject commonObject);
    }
}




package com.vinatti.dingdong.functions.mainhome.phathang.gachno.searchlist;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.GachNo;
import com.vinatti.dingdong.model.GachNoResult;
import com.vinatti.dingdong.model.SimpleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The ListGachNo Contract
 */
interface ListGachNoContract {

    interface Interactor extends IInteractor<Presenter> {
        void deliveryGetPaypostError(CommonCallback<GachNoResult> callback);
        void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode,
                             String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber,
                             String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType,
                             String signatureCapture, String note, String collectAmount, CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showData(ArrayList<GachNo> list);
        void showError(String message);
        void finishView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void paymentPayPost(List<CommonObject> paymentPaypostError);
    }
}




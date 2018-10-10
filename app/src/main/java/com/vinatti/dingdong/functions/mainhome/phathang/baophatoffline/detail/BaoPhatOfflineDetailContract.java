package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionInfo;
import com.vinatti.dingdong.model.SolutionResult;

import java.util.ArrayList;

/**
 * The BaoPhatBangKeDetail Contract
 */
interface BaoPhatOfflineDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void   paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signature, String note, String amount, CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatBangke();

        int getPosition();

        int getDeliveryType();

        int getPositionRow();

        void saveLocal(CommonObject baoPhat);

        void payment(CommonObject baoPhat);
    }
}




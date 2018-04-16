package com.vinatti.dingdong.functions.mainhome.phathang.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;

import java.util.ArrayList;

/**
 * The BaoPhatBangKeDetail Contract
 */
interface BaoPhatBangKeDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void getReasons(CommonCallback<ReasonResult> commonCallback);

        void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String sign, String note, CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);


        void showSuccessMessage(String message);

        void showError(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatBangke();

        void nextReceverPerson();

        void getReasons();

        void submitToPNS(String reason, String solution, String note, String sign);
    }
}




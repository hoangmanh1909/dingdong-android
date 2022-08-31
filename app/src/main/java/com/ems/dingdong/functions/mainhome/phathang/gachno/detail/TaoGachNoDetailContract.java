package com.ems.dingdong.functions.mainhome.phathang.gachno.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.PaymentPaypostRequest;

import java.util.ArrayList;

/**
 * The BaoPhatBangKeDetail Contract
 */
interface TaoGachNoDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void getReasons(CommonCallback<SimpleResult> commonCallback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String parcelCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback);

        void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback);


        void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);


        void showCallSuccess();

        void showUISolution(ArrayList<SolutionInfo> solutionInfos);

        void callAppToMpost();

        void showSuccessMessage(String message);

        void showError(String message);


        void finishView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatBangke();

        void getReasons();

        void callForward(String phone, String PostmanId, String POCode);

        void loadSolution(String code);

        void paymentDeliveryNoCodPayPost(String base64);

        void paymentDeliveryPayPost(String base64);

        int getPosition();

        int getDeliveryType();

        int getPositionRow();
    }
}




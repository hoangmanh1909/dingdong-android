package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.ArrayList;

/**
 * The BaoPhatBangKeDetail Contract
 */
interface BaoPhatBangKeDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void getReasons(CommonCallback<SimpleResult> commonCallback);


        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);

        void getSolutionByReasonCode(String code, CommonCallback<SimpleResult> commonCallback);


        void getInquiryAmount(String parcelCode, CommonCallback<InquiryAmountResult> callback);

        void updateMobile(String code,String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);

        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> simpleResultCommonCallback);

        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);

        void postImage(String path, CommonCallback<UploadSingleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);


        void showSuccessMessage(String message);

        void showError(String message);

        void showCallSuccess();

        void showSolution(ArrayList<SolutionInfo> solutionInfos);

        void showSuccess();

        void callAppToMpost();

        void finishView();

        void showView();

        void showImage(String file);

        void deleteFile();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatBangke();

        String getAmount();

        void getReasons();


        void submitToPNS(String reason, String solution, String note, String sign);

        void callForward(String phone);

        void loadSolution(String code);

        void signDataAndSubmitToPNS(String base64);

        void paymentDelivery(String base64);

        int getPosition();

        int getDeliveryType();

        int getPositionRow();

        void updateMobile(String phone);

        void postImage(String path);
    }
}




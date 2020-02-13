package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;

import java.util.ArrayList;
import java.util.List;

public interface XacNhanBaoPhatContract {
    interface Interactor extends IInteractor<Presenter> {
        void getReasons(CommonCallback<ReasonResult> commonCallback);
        void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback);
        void postImage(String path, CommonCallback<UploadSingleResult> callback);
        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> simpleResultCommonCallback);
    }
    interface View extends PresentView<Presenter> {
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);
        void showSolution(ArrayList<SolutionInfo> solutionInfos);
        void showImage(String file);
        void deleteFile();
        void showError(String message);
        void showSuccess(String code);
        void finishView();
    }
    interface Presenter extends IPresenter<View, Interactor> {
        List<DeliveryPostman> getBaoPhatBangke();
        void getReasons();
        void loadSolution(String code);
        void postImage(String path);
        void submitToPNS(String reason, String solution, String note, String sign);
        void paymentDelivery(String deliveryImage,String signCapture);
    }
}

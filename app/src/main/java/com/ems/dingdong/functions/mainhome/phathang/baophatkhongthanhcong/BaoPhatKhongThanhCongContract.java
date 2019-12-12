package com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.ArrayList;

/**
 * The BaoPhatKhongThanhCong Contract
 */
interface BaoPhatKhongThanhCongContract {

    interface Interactor extends IInteractor<Presenter> {
        void getReasons(CommonCallback<ReasonResult> commonCallback);


        void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback);
        void checkLadingCode(String parcelCode, CommonCallback<SimpleResult> callback);

        void pushToPNS(PushToPnsRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        void showSolutionSuccess(ArrayList<SolutionInfo> solutionInfos);

        void viewFinish();

        void showMessageStatus(SimpleResult result);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        void getReasons();

        void pushToPNS(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate,
                       String deliveryTime, String receiverName, String status, String reasonCode, String solutionCode, String note, String ladingPostmanID, String routeCode);

        void getSolutionByReasonCode(String code);
        void checkLadingCode(String parcelCode);
    }
}




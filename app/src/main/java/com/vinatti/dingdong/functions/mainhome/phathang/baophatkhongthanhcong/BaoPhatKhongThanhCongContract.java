package com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.ReasonInfo;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionInfo;
import com.vinatti.dingdong.model.SolutionResult;

import java.util.ArrayList;

/**
 * The BaoPhatKhongThanhCong Contract
 */
interface BaoPhatKhongThanhCongContract {

    interface Interactor extends IInteractor<Presenter> {
        void getReasons(CommonCallback<ReasonResult> commonCallback);

        void pushToPNS(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String status, String reasonCode, String solutionCode, String note, CommonCallback<SimpleResult> commonCallback);

        void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void getReasonsSuccess(ArrayList<ReasonInfo> reasonInfos);

        void showSolutionSuccess(ArrayList<SolutionInfo> solutionInfos);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        void getReasons();

        void pushToPNS(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String status, String reasonCode, String solutionCode, String note);

        void getSolutionByReasonCode(String code);
    }
}




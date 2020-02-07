package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;

import java.util.ArrayList;

/**
 * The CreateBd13 Contract
 */
interface CreateBd13Contract {

    interface Interactor extends IInteractor<Presenter> {
        void bD13AddNew(Bd13Create json, CommonCallback<SimpleResult> commonCallback);
        void searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest,CommonCallback<DeliveryPostmanResponse> commonCallback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);
        void updateMobile(String code, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showSuccessMessage(String message);

        void showListSuccess(ArrayList<DeliveryPostman> list);

        void showListEmpty();
        void showCallSuccess();

        void showSuccess();

        void showError(String message);

        void showView();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showBarcode(BarCodeCallback barCodeCallback);

        void postBD13AddNew(Bd13Create bd13Create);

        void searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest);

        void callForward(String phone,String parcelCode);

        void updateMobile(String phone,String parcelCode);
    }
}




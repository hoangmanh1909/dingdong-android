package com.ems.dingdong.functions.mainhome.phathang.gachno;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentPaypostRequest;

import java.util.List;

/**
 * The TaoGachNo Contract
 */
interface TaoGachNoContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback);

        void callForwardCallCenter(String callerNumber, String calleeNumber,
                                   String callForwardType, String hotlineNumber,
                                   String ladingCode, CommonCallback<SimpleResult> callback);


        void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showData(CommonObject commonObject);

        void showSuccessMessage(String message);

        void showCallSuccess();

        void showError(String message);

        void finishView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        void searchParcelCodeDelivery(String parcelCode);

        void showDetail(CommonObject commonObject, int position);

        void pushViewConfirmAll(List<CommonObject> list);

        void callForward(String phone, String ladingCode);

        void showViewList();
    }
}




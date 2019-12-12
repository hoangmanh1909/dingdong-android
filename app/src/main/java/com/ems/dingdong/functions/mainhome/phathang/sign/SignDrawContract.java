package com.ems.dingdong.functions.mainhome.phathang.sign;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

import java.util.List;

/**
 * The SignDraw Contract
 */
interface SignDrawContract {

    interface Interactor extends IInteractor<Presenter> {

        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback);

        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showError(String message);

        void showSuccess();

        void showSuccessMessage(String message);

        void callAppToMpost();

        void finishView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<CommonObject> getBaoPhatCommon();

        void signDataAndSubmitToPNS(String base64);

        void paymentDelivery(String base64);
    }
}




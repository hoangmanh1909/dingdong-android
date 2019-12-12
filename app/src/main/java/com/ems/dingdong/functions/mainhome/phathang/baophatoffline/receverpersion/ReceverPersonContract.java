package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.receverpersion;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;

import java.util.List;

/**
 * The ReceverPerson Contract
 */
interface ReceverPersonContract {

    interface Interactor extends IInteractor<Presenter> {

        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showError(String message);
        void finishView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<CommonObject> getBaoPhatCommon();
        void payment();

        void saveLocal();
    }
}




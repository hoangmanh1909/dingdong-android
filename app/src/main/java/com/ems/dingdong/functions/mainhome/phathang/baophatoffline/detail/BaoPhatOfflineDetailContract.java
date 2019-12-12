package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;

/**
 * The BaoPhatBangKeDetail Contract
 */
interface BaoPhatOfflineDetailContract {

    interface Interactor extends IInteractor<Presenter> {

        void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback);

        void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatBangke();

        int getPosition();

        int getDeliveryType();

        int getPositionRow();

        void saveLocal(CommonObject baoPhat);

        void payment(CommonObject baoPhat);

        void submitToPNS();
    }
}




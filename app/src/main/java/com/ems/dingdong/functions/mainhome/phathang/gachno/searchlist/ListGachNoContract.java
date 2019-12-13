package com.ems.dingdong.functions.mainhome.phathang.gachno.searchlist;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.GachNo;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentPaypostRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * The ListGachNo Contract
 */
interface ListGachNoContract {

    interface Interactor extends IInteractor<Presenter> {
        void deliveryGetPaypostError(String fromDate, String toDate, CommonCallback<GachNoResult> callback);

        void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showData(ArrayList<GachNo> list);

        void showError(String message);

        void finishView();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void paymentPayPost(List<CommonObject> paymentPaypostError);

        void getList(String fromDate, String toDate);
    }
}




package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DingDongGetCancelDelivery;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;

import java.util.ArrayList;
import java.util.List;

public interface CancelBD13Contract {
    interface Interactor extends IInteractor<Presenter> {
        void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode, CommonCallback<DingDongGetCancelDeliveryResponse> commonCallback);
        void cancelDelivery(List<DingDongCancelDeliveryRequest> dingDongGetCancelDeliveryRequestList, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter>
    {
        void showListSuccess(ArrayList<DingDongGetCancelDelivery> list);

        void showListEmpty();

        void showView(String message);
    }

    interface Presenter extends IPresenter<View,Interactor>
    {
        void showBarcode(BarCodeCallback barCodeCallback);
        void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode);
        void cancelDelivery(List<DingDongCancelDeliveryRequest> dingDongGetCancelDeliveryRequestList);
    }
}

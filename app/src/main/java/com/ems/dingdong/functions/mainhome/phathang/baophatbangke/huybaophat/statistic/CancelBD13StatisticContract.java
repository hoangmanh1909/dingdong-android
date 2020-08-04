package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.CancelDeliveryResult;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;
import com.ems.dingdong.model.response.CancelStatisticItem;

import java.util.List;

import io.reactivex.Observable;

public interface CancelBD13StatisticContract {
    interface View extends PresentView<Presenter> {
        void showListSuccess(List<CancelStatisticItem> resultList);

        void showError(String message);
    }

    interface Interactor extends IInteractor<Presenter> {
        Observable<CancelDeliveryResult> getCancelDeliveryStatic(CancelDeliveryStatisticRequest request);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getCancelDeliveryStatic(String poCode, String postmanCode, String routeCode, Integer fromDate, Integer toDate, String statusCode);

        ContainerView getContainerView();

        void showBarcode(BarCodeCallback barCodeCallback);

        void titleChanged(int quantity, int currentSetTab);

        List<CancelStatisticItem> getListFromMap(String ladingCode);

        int getCurrentTab();
    }
}

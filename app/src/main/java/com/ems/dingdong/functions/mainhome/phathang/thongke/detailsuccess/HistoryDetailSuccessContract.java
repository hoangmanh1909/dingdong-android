package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.model.response.StatisticDeliveryGeneralResponse;

import java.util.ArrayList;

/**
 * The History Contract
 */
interface HistoryDetailSuccessContract {

    interface Interactor extends IInteractor<Presenter> {

        void statisticDeliveryGeneral(String postmanID, String fromDate, String toDate, CommonCallback<StatisticDeliveryGeneralResult> statisticDeliveryGeneralResultCommonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(ArrayList<StatisticDeliveryGeneralResponse> list);

        void showListEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void statisticDeliveryGeneral(String id, String fromDate, String toDate);

        void showDetail(String serviceCode, String serviceName, int typeDelivery);
    }
}




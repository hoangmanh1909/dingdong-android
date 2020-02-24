package com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.model.response.StatisticDeliveryDetailResponse;

import java.util.ArrayList;

/**
 * The CommonObject Contract
 */
interface ListDeliverySuccessDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        void statisticDeliveryDetail(String serviceCode, int typeDelivery, String postmanID, String fromDate, String toDate, boolean isSuccess, String routeCode, CommonCallback<StatisticDeliveryDetailResult> callback);
    }

    interface View extends PresentView<Presenter> {

        void showListSuccess(ArrayList<StatisticDeliveryDetailResponse> list);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getServiceName();

        boolean getIsSuccess();
    }
}




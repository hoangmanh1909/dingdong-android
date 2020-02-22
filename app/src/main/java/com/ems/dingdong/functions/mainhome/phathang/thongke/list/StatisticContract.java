package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;

import java.util.ArrayList;

/**
 * The Statistic Contract
 */
interface StatisticContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchDeliveryStatistic(String fromDate, String status,
                                     String postmanId, String shift, String routeCode, CommonCallback<CommonObjectListResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(ArrayList<CommonObject> list);

        void showListEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void search(String fromDate, String status, String shift, String routeCode);

        String getStatus();

        void pushViewDetail(String parcelCode);
    }
}




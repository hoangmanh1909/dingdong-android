package com.ems.dingdong.functions.mainhome.phathang.thongke.list;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.SimpleResult;

import java.util.ArrayList;

/**
 * The Statistic Contract
 */
interface StatisticContract {

    interface Interactor extends IInteractor<Presenter> {
        /**
         * Search all general statistic delivery record.
         * @param fromDate from create date
         * @param toDate to create date
         * @param status status of delivery
         *               "C14" - success, "C18" - not success
         * @param postmanId from postman id, it can be got from share preference on UserInfo
         * @param routeCode from route id, it can be got from share preference on RouteInfo
         */
        void searchDeliveryStatistic(String fromDate, String toDate, String status,
                                     String postmanId, String routeCode, CommonCallback<CommonObjectListResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(ArrayList<CommonObject> list);

        void showListEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void search(String fromDate, String toDate, String status, String routeCode);

        /**
         * Get status of tabs
         * @return "C14" - success, "C18" - not succes
         */
        String getStatus();

        /**
         * Added detail fragment
         * @param parcelCode
         */
        void pushViewDetail(String parcelCode);

        /**
         * Set count on title tab.
         * @param count total count
         */
        void setCount(int count);

        /**
         *
         * @param fromDate
         * @param toDate
         */
        void onSearched(String fromDate, String toDate);
    }
}




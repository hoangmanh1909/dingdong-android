package com.ems.dingdong.functions.mainhome.gomhang.statistic;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticCollect;
import com.ems.dingdong.model.StatisticCollectResult;

import java.util.ArrayList;

/**
 * The CommonObject Contract
 */
interface ListStatisticContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchStatisticCollect(
                String postmanID,
                String fromDate,
                String toDate, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(ArrayList<StatisticCollect> list);

        void showError(String message);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchStatisticCollect(
                String postmanID,
                String fromDate,
                String toDate);

        void showDetailView(StatisticCollect statisticCollect);
    }
}




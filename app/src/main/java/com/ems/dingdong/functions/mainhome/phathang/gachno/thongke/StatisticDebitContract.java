package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDebitGeneralResult;
import com.ems.dingdong.model.response.StatisticDebitGeneralResponse;

interface StatisticDebitContract {

    interface Interactor extends IInteractor<Presenter> {
        void getDebitStatistic(String postmanID, String fromDate, String toDate,
                               CommonCallback<StatisticDebitGeneralResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showStatistic(StatisticDebitGeneralResponse value);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showStatistic(String postmanID, String fromDate, String toDate);

        void showDetail(String statusCode, String fromDate, String toDate);
    }
}

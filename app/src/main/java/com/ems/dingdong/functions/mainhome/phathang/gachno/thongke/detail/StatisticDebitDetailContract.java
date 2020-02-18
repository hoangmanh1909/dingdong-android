package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.model.response.StatisticDebitDetailResponse;

import java.util.ArrayList;

interface StatisticDebitDetailContract {
    interface Interactor extends IInteractor<Presenter> {

        void statisticDebitDetail(String postmanID, String fromDate, String toDate, String statusCode,
                           CommonCallback<StatisticDebitDetailResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListDetail(ArrayList<StatisticDebitDetailResponse> list);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void statisticDebitDetail(String postmanID, String fromDate, String toDate);

        void showDetail();

        String getStatusCode();
    }
}

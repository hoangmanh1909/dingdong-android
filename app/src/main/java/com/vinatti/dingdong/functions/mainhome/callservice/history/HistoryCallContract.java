package com.vinatti.dingdong.functions.mainhome.callservice.history;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.HistoryCallInfo;
import com.vinatti.dingdong.model.HistoryCallResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The History Contract
 */
interface HistoryCallContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchCallCenter(String postmanID,
                              String fromDate,
                              String toDate, CommonCallback<HistoryCallResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<HistoryCallInfo> list);

        void showListEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getParcelCode();

        void getHistory(String fromDate, String toDate);
    }
}




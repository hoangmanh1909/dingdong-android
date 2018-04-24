package com.vinatti.dingdong.functions.mainhome.callservice.history;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.SimpleResult;

import java.util.ArrayList;

/**
 * The History Contract
 */
interface HistoryCallContract {

    interface Interactor extends IInteractor<Presenter> {
        void searchCallCenter(String postmanID,
                              String fromDate,
                              String toDate, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(ArrayList<CommonObject> list);

        void showListEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getParcelCode();

        void getHistory(String fromDate, String toDate);
    }
}




package com.vinatti.dingdong.functions.mainhome.phathang.thongke.history;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.CommonObjectListResult;

import java.util.ArrayList;

/**
 * The History Contract
 */
interface HistoryContract {

    interface Interactor extends IInteractor<Presenter> {
        void getHistoryDelivery(String parcelCode, CommonCallback<CommonObjectListResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(ArrayList<CommonObject> list);

        void showListEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getParcelCode();

        void getHistory();
    }
}




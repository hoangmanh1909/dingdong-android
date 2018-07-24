package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectListResult;

/**
 * The ListBd13 Contract
 */
interface ListBd13Contract {

    interface Interactor extends IInteractor<Presenter> {
        void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift, CommonCallback<CommonObjectListResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift);
    }
}




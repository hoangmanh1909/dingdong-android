package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.listbd13;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.Bd13Code;
import com.ems.dingdong.model.HistoryCreateBd13Result;

import java.util.List;

/**
 * The ListBd13 Contract
 */
interface ListBd13Contract {

    interface Interactor extends IInteractor<Presenter> {
        void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift, CommonCallback<HistoryCreateBd13Result> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showResponseSuccess(List<Bd13Code> list);

        void showResponseEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift);
    }
}




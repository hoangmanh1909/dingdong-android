package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.create;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.BarCodeCallback;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.Bd13Create;
import com.vinatti.dingdong.model.SimpleResult;

/**
 * The CreateBd13 Contract
 */
interface CreateBd13Contract {

    interface Interactor extends IInteractor<Presenter> {
        void bD13AddNew(Bd13Create json, CommonCallback<SimpleResult> commonCallback);
    }

    interface View extends PresentView<Presenter> {
        void showSuccessMessage(String message);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showBarcode(BarCodeCallback barCodeCallback);

        void postBD13AddNew(Bd13Create bd13Create);
    }
}




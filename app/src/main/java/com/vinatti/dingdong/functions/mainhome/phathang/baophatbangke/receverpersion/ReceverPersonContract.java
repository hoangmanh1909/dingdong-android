package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.receverpersion;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.model.CommonObject;

/**
 * The ReceverPerson Contract
 */
interface ReceverPersonContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatBangke();

        void nextViewSign();
    }
}




package com.vinatti.dingdong.functions.mainhome.phathang.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.model.CommonObject;

/**
 * The BaoPhatBangKeDetail Contract
 */
interface BaoPhatBangKeDetailContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        CommonObject getBaoPhatBangke();

        void nextReceverPerson();
    }
}




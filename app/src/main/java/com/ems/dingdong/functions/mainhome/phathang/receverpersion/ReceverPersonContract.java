package com.ems.dingdong.functions.mainhome.phathang.receverpersion;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.CommonObject;

import java.util.List;

/**
 * The ReceverPerson Contract
 */
interface ReceverPersonContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<CommonObject> getBaoPhatCommon();

        void nextViewSign();
    }
}




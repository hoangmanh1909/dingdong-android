package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

/**
 * The Profile Contract
 */
interface ProfileContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void moveToEWallet();

        void showLichsuCuocgoi();
        void showLuong();
    }
}




package com.vinatti.dingdong.functions.mainhome.home;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

/**
 * The Home Contract
 */
interface HomeContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showViewCreateBd13();

        void showViewListCreateBd13();
    }
}




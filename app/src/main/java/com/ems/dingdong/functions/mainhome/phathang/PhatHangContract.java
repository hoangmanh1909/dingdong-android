package com.ems.dingdong.functions.mainhome.phathang;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

/**
 * The PhatHang Contract
 */
interface PhatHangContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showViewStatisticPtc(boolean isSuccess);
        void showViewCancelBd13();
        void showListOffline();
        void showNhapBaoPhatOffline();
        void showStatisticDebit();
    }
}




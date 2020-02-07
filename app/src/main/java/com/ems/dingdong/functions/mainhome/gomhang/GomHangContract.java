package com.ems.dingdong.functions.mainhome.gomhang;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

/**
 * The GomHang Contract
 */
interface GomHangContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {

    }


    interface Presenter extends IPresenter<View, Interactor> {
        void showListStatistic();
        void showListHoanTatNhieuTin();
        void showXacNhanDiaChiPresenter();
    }
}




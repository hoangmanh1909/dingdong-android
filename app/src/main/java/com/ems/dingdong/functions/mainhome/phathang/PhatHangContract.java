package com.ems.dingdong.functions.mainhome.phathang;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;
import com.ems.dingdong.model.SimpleResult;

import io.reactivex.Single;

/**
 * The PhatHang Contract
 */
interface PhatHangContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> searchTu(String request);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void searchTu (String tu);

        void showViewStatisticPtc(StatisticType statisticType);

        void showViewCancelBd13();

        void showListOffline();

        void showNhapBaoPhatOffline();

        void showStatisticDebit();

        void showLocation();

        void showStatisticForward();

        void showPayment();

        void showStatisticSML();
    }
}




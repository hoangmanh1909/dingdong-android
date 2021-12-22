package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

public interface ListBankContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showEwallet();
    }
}

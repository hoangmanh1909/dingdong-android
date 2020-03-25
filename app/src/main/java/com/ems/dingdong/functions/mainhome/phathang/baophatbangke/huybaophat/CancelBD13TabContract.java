package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

public interface CancelBD13TabContract {
    interface View extends PresentView<Presenter> {

    }

    interface Interactor extends IInteractor<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        ContainerView getContainerView();
    }

}

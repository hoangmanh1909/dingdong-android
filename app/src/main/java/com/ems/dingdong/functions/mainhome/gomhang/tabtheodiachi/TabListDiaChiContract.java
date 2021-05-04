package com.ems.dingdong.functions.mainhome.gomhang.tabtheodiachi;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

public class TabListDiaChiContract {
    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        ContainerView getContainerView();

        int getType();
    }
}

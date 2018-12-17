package com.ems.dingdong.functions.mainhome.phathang.thongke.tabs;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

/**
 * The ThongKeTabs Contract
 */
interface ThongKeTabsContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        ContainerView getContainerView();
    }
}




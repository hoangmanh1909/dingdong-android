package com.ems.dingdong.functions.mainhome.phathang.routemanager;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.IView;

public interface RouteTabsConstract {

    interface View extends IView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        ContainerView getContainerView();
    }

    interface Interactor extends IInteractor<Presenter> {

    }

    interface OnTabsListener {

        void setQuantity(int quantity, int currentSetTab);

        void onTabChange(int position);
    }

}

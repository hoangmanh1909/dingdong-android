package com.ems.dingdong.functions.mainhome.phathang.routemanager;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.IView;

public interface RouteTabsConstract {

    interface View extends IView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        String getMode();
        ContainerView getContainerView();
    }

    interface Interactor extends IInteractor<Presenter> {

    }

    /**
     * Interface to handle event between 2 tabs.
     */
    interface OnTabsListener {

        /**
         * Event set count of title tab
         */
        void setQuantity(int quantity, int currentSetTab);

        void onTabChange(int position);


    }

}

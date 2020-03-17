package com.ems.dingdong.functions.mainhome.phathang.routemanager;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class RouteTabsPresenter extends Presenter<RouteTabsConstract.View, RouteTabsConstract.Interactor> implements RouteTabsConstract.Presenter {

    public RouteTabsPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public RouteTabsConstract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public RouteTabsConstract.View onCreateView() {
        return RouteTabsFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

package com.ems.dingdong.functions.mainhome.phathang.routemanager;

import android.app.Activity;
import android.os.Bundle;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.utiles.Constants;

public class RouteTabsPresenter extends Presenter<RouteTabsConstract.View, RouteTabsConstract.Interactor> implements RouteTabsConstract.Presenter {

    String mode = Constants.ROUTE_CHANGE_DELIVERY;

    public RouteTabsPresenter(ContainerView containerView) {
        super(containerView);

    }


    @Override
    public void start() {
        Bundle extras = ((Activity) mContainerView).getIntent().getExtras();
        if (extras != null) {
            this.mode = extras.getString(Constants.ROUTE_CHANGE_MODE);
        }
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
    public String getMode() {
        return mode;
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

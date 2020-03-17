package com.ems.dingdong.functions.mainhome.phathang.routemanager;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.base.DingDongActivity;

public class RouteTabsActivity extends DingDongActivity {

    private RouteTabsFragment routeTabsFragment;

    @Override
    public ViewFragment onCreateFirstFragment() {
        routeTabsFragment = (RouteTabsFragment) new RouteTabsPresenter(this).getFragment();
        return routeTabsFragment;
    }
}

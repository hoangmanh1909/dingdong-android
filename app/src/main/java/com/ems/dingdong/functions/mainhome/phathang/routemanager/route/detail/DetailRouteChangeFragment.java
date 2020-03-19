package com.ems.dingdong.functions.mainhome.phathang.routemanager.route.detail;

import com.core.base.viper.ViewFragment;
import com.ems.dingdong.R;

public class DetailRouteChangeFragment extends ViewFragment<DetailRouteChangeConstract.Presenter> implements DetailRouteChangeConstract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_route_detail;
    }

    @Override
    public void showViewDetail() {

    }

    @Override
    public void initLayout() {
        super.initLayout();
        mPresenter.getChangeRouteDetail();
    }

    public static DetailRouteChangeFragment getInstance() {
        return new DetailRouteChangeFragment();
    }
}

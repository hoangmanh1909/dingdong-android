package com.ems.dingdong.functions.mainhome.phathang.thongke.tabs;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

/**
 * The ThongKeTabs Presenter
 */
public class ThongKeTabsPresenter extends Presenter<ThongKeTabsContract.View, ThongKeTabsContract.Interactor>
        implements ThongKeTabsContract.Presenter {

    public ThongKeTabsPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ThongKeTabsContract.View onCreateView() {
        return ThongKeTabsFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ThongKeTabsContract.Interactor onCreateInteractor() {
        return new ThongKeTabsInteractor(this);
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

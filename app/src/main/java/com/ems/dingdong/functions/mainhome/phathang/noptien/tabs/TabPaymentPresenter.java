package com.ems.dingdong.functions.mainhome.phathang.noptien.tabs;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TabPaymentPresenter extends Presenter<TabPaymentContract.View, TabPaymentContract.Interactor>
        implements TabPaymentContract.Presenter {

    public TabPaymentPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TabPaymentContract.Interactor onCreateInteractor() {
        return new TabPaymentInteractor(this);
    }

    @Override
    public TabPaymentContract.View onCreateView() {
        return TabPaymentFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

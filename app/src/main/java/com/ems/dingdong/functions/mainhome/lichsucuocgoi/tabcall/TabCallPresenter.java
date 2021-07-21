package com.ems.dingdong.functions.mainhome.lichsucuocgoi.tabcall;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TabCallPresenter extends Presenter<TabCallContract.View ,TabCallContract.Interactor> implements  TabCallContract.Presenter {

    public TabCallPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public void start() {

    }

    @Override
    public TabCallContract.Interactor onCreateInteractor() {
        return new TabCallInteractor(this);
    }

    @Override
    public TabCallContract.View onCreateView() {
        return TabCallFragment.getInstance();
    }
}

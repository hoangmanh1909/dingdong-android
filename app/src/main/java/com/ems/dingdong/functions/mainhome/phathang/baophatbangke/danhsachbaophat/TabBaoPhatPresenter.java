package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.danhsachbaophat;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TabBaoPhatPresenter extends Presenter<TabBaoPhatConstract.View, TabBaoPhatConstract.Interactor> implements TabBaoPhatConstract.Presenter {

    public TabBaoPhatPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TabBaoPhatConstract.Interactor onCreateInteractor() {
        return new TabBaoPhatInteractor(this);
    }

    @Override
    public TabBaoPhatConstract.View onCreateView() {
        return TabBaoPhatFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabtaobanke;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TabCreatePresenter extends Presenter<TabCreateContract.View, TabCreateContract.Interactor>
        implements TabCreateContract.Presenter {


    public TabCreatePresenter(ContainerView containerView) {
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
    public TabCreateContract.Interactor onCreateInteractor() {
        return new TabCreateInteractor(this);
    }

    @Override
    public TabCreateContract.View onCreateView() {
        return TabCreateFragment.getInstance();
    }
}

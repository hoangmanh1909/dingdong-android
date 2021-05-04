package com.ems.dingdong.functions.mainhome.gomhang.tabtheodiachi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TabListDiaChiPresenter extends Presenter<TabListDiaChiContract.View, TabListDiaChiContract.Interactor>
        implements TabListDiaChiContract.Presenter {

    public TabListDiaChiPresenter(ContainerView containerView) {
        super(containerView);
    }


    int mType;

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public int getType() {
        return mType;
    }

    public TabListDiaChiPresenter setType(int type) {
        mType = type;
        return this;
    }

    @Override
    public void start() {

    }

    @Override
    public TabListDiaChiContract.Interactor onCreateInteractor() {
        return new TabListDiaChiInteractor(this);
    }

    @Override
    public TabListDiaChiContract.View onCreateView() {
        return TabListDiachiFragment.getInstance();
    }
}

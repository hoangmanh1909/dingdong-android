package com.ems.dingdong.functions.mainhome.gomhang.new_xacnhantin;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.gomhang.tabliscommon.TabListCommonContract;
import com.ems.dingdong.functions.mainhome.gomhang.tabliscommon.TabListCommonPresenter;

public class TabConFirmPresenter extends Presenter<TabConFirmContract.View, TabConFirmContract.Interactor>
        implements TabConFirmContract.Presenter {

    public TabConFirmPresenter(ContainerView containerView) {
        super(containerView);
    }

    int mType;

    @Override
    public void start() {

    }

    @Override
    public TabConFirmContract.Interactor onCreateInteractor() {
        return new TabConFirmInteractor(this);
    }

    @Override
    public TabConFirmContract.View onCreateView() {
        return TabConFirmFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public int getType() {
        return mType;
    }

    public TabConFirmPresenter setType(int type) {
        mType = type;
        return this;
    }
}

package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class CancelBD13TabPresenter extends Presenter<CancelBD13TabContract.View, CancelBD13TabContract.Interactor> implements CancelBD13TabContract.Presenter {

    public CancelBD13TabPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public CancelBD13TabContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public CancelBD13TabContract.View onCreateView() {
        return CancelBD13TabFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

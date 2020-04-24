package com.ems.dingdong.calls.diapad;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class DiapadPresenter extends Presenter<DiapadConstract.View, DiapadConstract.Interactor> {

    public DiapadPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public DiapadConstract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public DiapadConstract.View onCreateView() {
        return DiapadFragment.getInstance();
    }


}

package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TimDuongDiPresenter extends Presenter<TimDuongDiContract.View, TimDuongDiContract.Interactor> implements TimDuongDiContract.Presenter {
    public TimDuongDiPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TimDuongDiContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public TimDuongDiContract.View onCreateView() {
        return null;
    }
}

package com.ems.dingdong.functions.mainhome.profile.tienluong;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class SalaryPresenter extends Presenter<SalaryContract.View, SalaryContract.Interactor>
        implements SalaryContract.Presenter {

    public SalaryPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public SalaryContract.Interactor onCreateInteractor() {
        return new SalaryInteractor(this);
    }

    @Override
    public SalaryContract.View onCreateView() {
        return SalaryFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

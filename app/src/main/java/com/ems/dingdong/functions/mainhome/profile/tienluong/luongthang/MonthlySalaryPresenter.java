package com.ems.dingdong.functions.mainhome.profile.tienluong.luongthang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class MonthlySalaryPresenter extends Presenter<MonthlySalaryContract.View, MonthlySalaryContract.Interactor>
        implements MonthlySalaryContract.Presenter {

    public MonthlySalaryPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public MonthlySalaryContract.Interactor onCreateInteractor() {
        return new MonthlySalaryInteractor(this);
    }

    @Override
    public MonthlySalaryContract.View onCreateView() {
        return MonthlySalaryFragment.getInstance();
    }
}

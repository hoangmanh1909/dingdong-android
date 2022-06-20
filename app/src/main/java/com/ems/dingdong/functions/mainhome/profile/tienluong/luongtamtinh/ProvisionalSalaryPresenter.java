package com.ems.dingdong.functions.mainhome.profile.tienluong.luongtamtinh;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class ProvisionalSalaryPresenter extends Presenter<ProvisionalSalaryContract.View, ProvisionalSalaryContract.Interactor>
        implements ProvisionalSalaryContract.Presenter {

    public ProvisionalSalaryPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ProvisionalSalaryContract.Interactor onCreateInteractor() {
        return new ProvisionalSalaryInteractor(this);
    }

    @Override
    public ProvisionalSalaryContract.View onCreateView() {
        return ProvisionalSalaryFragment.getInstance();
    }
}

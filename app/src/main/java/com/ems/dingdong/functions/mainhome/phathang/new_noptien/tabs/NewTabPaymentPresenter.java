package com.ems.dingdong.functions.mainhome.phathang.new_noptien.tabs;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class NewTabPaymentPresenter extends Presenter<NewTabPaymentContract.View, NewTabPaymentContract.Interactor>
        implements NewTabPaymentContract.Presenter {

    public NewTabPaymentPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public NewTabPaymentContract.Interactor onCreateInteractor() {
        return new NewTabPaymentInteractor(this);
    }

    @Override
    public NewTabPaymentContract.View onCreateView() {
        return NewTabPaymentFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

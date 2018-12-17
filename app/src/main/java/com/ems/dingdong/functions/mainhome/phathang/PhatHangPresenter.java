package com.ems.dingdong.functions.mainhome.phathang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

/**
 * The PhatHang Presenter
 */
public class PhatHangPresenter extends Presenter<PhatHangContract.View, PhatHangContract.Interactor>
        implements PhatHangContract.Presenter {

    public PhatHangPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public PhatHangContract.View onCreateView() {
        return PhatHangFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public PhatHangContract.Interactor onCreateInteractor() {
        return new PhatHangInteractor(this);
    }
}

package com.vinatti.dingdong.functions.mainhome.gomhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

/**
 * The GomHang Presenter
 */
public class GomHangPresenter extends Presenter<GomHangContract.View, GomHangContract.Interactor>
        implements GomHangContract.Presenter {

    public GomHangPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public GomHangContract.View onCreateView() {
        return GomHangFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public GomHangContract.Interactor onCreateInteractor() {
        return new GomHangInteractor(this);
    }
}

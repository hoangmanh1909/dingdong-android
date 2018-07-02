package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.tabs;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

/**
 * The BangKeBaoPhatContainer Presenter
 */
public class BangKeBaoPhatContainerPresenter extends Presenter<BangKeBaoPhatContainerContract.View, BangKeBaoPhatContainerContract.Interactor>
        implements BangKeBaoPhatContainerContract.Presenter {

    public BangKeBaoPhatContainerPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BangKeBaoPhatContainerContract.View onCreateView() {
        return BangKeBaoPhatContainerFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public BangKeBaoPhatContainerContract.Interactor onCreateInteractor() {
        return new BangKeBaoPhatContainerInteractor(this);
    }
}

package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class LinkEWalletPresenter extends Presenter<LinkEWalletContract.View, LinkEWalletContract.Interactor>
        implements LinkEWalletContract.Presenter {

    public LinkEWalletPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void getEWalletToken() {

    }

    @Override
    public void start() {

    }

    @Override
    public LinkEWalletContract.Interactor onCreateInteractor() {
        return new LinkEWalletInteractor(this);
    }

    @Override
    public LinkEWalletContract.View onCreateView() {
        return LinkEWalletFragment.getInstance();
    }
}

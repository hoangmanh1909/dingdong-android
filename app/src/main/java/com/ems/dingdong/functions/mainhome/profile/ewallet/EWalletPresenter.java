package com.ems.dingdong.functions.mainhome.profile.ewallet;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet.LinkEWalletPresenter;

public class EWalletPresenter extends Presenter<EWalletContract.View, EWalletContract.Interactor> implements EWalletContract.Presenter {

    private int typeEWallet = 1;
    public EWalletPresenter(ContainerView containerView) {
        super(containerView);
    }

    public EWalletPresenter setTypeEWallet(int typeEWallet){
        this.typeEWallet = typeEWallet;
        return this;
    }

    @Override
    public void start() {

    }

    @Override
    public EWalletContract.Interactor onCreateInteractor() {
        return new EWalletInteractor(this);
    }

    @Override
    public EWalletContract.View onCreateView() {
        return EWalletFragment.getInstance();
    }

    @Override
    public void showLinkEWalletFragment() {
        new LinkEWalletPresenter(mContainerView).setTypeEWallet(typeEWallet).pushView();
    }

    @Override
    public int getTypeEWallet() {
        return typeEWallet;
    }
}

package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;

public class ListBankPresenter  extends Presenter<ListBankContract.View,ListBankContract.Interactor> implements ListBankContract.Presenter {

    public ListBankPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public ListBankContract.Interactor onCreateInteractor() {
        return new ListBankInteractor(this);
    }

    @Override
    public ListBankContract.View onCreateView() {
        return ListBankFragment.getInstance();
    }

    @Override
    public void showEwallet() {
        new EWalletPresenter(mContainerView).pushView();
    }
}

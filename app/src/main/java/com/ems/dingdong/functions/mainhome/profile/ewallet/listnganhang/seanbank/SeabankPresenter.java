package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.seanbank;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class SeabankPresenter extends Presenter<SeabankContract.View, SeabankContract.Interactor> implements SeabankContract.Presenter {

    public SeabankPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public SeabankContract.Interactor onCreateInteractor() {
        return new SeabankInteractor(this);
    }

    @Override
    public SeabankContract.View onCreateView() {
        return SeabankFragment.getInstance();
    }
}

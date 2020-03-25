package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class CancelBD13StatisticPresenter extends Presenter<CancelBD13StatisticContract.View, CancelBD13StatisticContract.Interactor> implements CancelBD13StatisticContract.Presenter {

    public CancelBD13StatisticPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public CancelBD13StatisticContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public CancelBD13StatisticContract.View onCreateView() {
        return CancelBD13StatisticFragment.getInstance();
    }
}

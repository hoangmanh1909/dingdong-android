package com.ems.dingdong.functions.mainhome.phathang.thongke.ticket.tabthongke;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TabStatisticTicketPresenter extends Presenter<TabStatisticTicketContract.View, TabStatisticTicketContract.Interactor>
        implements TabStatisticTicketContract.Presenter {

    public TabStatisticTicketPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TabStatisticTicketContract.Interactor onCreateInteractor() {
        return new TabStatisticTicketInteractor(this);
    }

    @Override
    public TabStatisticTicketContract.View onCreateView() {
        return TabStatisticTicketFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

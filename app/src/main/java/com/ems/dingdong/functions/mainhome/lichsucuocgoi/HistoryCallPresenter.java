package com.ems.dingdong.functions.mainhome.lichsucuocgoi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentPresenter;

public class HistoryCallPresenter extends Presenter<HistoryCallContract.View, HistoryCallContract.Interactor> implements HistoryCallContract.Presenter {

    private HistoryCallContract.OnTabListener tabListener;

    public HistoryCallPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public HistoryCallContract.Interactor onCreateInteractor() {
        return new HistoryCallInteractor(this);
    }

    @Override
    public HistoryCallContract.View onCreateView() {
        return HistoryCallFragment.getInstance();
    }


    public HistoryCallPresenter setOnTabListener(HistoryCallContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }

}

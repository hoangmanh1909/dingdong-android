package com.ems.dingdong.functions.mainhome.gomhang.tabliscommon;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonPresenter;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentContract;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;

public class TabListCommonPresenter extends Presenter<TabListCommonContract.View, TabListCommonContract.Interactor>
        implements TabListCommonContract.Presenter {

    public TabListCommonPresenter(ContainerView containerView) {
        super(containerView);
    }


    int mType;

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public int getType() {
        return mType;
    }

    public TabListCommonPresenter setType(int type) {
        mType = type;
        return this;
    }

    @Override
    public void start() {

    }

    @Override
    public TabListCommonContract.Interactor onCreateInteractor() {
        return new TabListCommonInteractor(this);
    }

    @Override
    public TabListCommonContract.View onCreateView() {
        return TabListCommonFragment.getInstance();
    }
}

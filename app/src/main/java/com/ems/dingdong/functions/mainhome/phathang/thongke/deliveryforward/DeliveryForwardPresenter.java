package com.ems.dingdong.functions.mainhome.phathang.thongke.deliveryforward;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class DeliveryForwardPresenter extends Presenter<DeliveryForwardContract.View, DeliveryForwardContract.Interactor>
        implements DeliveryForwardContract.Presenter {

    public DeliveryForwardPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public DeliveryForwardContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public DeliveryForwardContract.View onCreateView() {
        return DeliveryForwardFragment.getInstance();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }
}

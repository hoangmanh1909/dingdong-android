package com.ems.dingdong.functions.mainhome.address;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.XacMinhDiaChiPresenter;

public class AddressPresenter extends Presenter<AddressContract.View, AddressContract.Interactor>
        implements AddressContract.Presenter {

    public AddressPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void showXacMinhDiaChi() {
        new XacMinhDiaChiPresenter(mContainerView).pushView();
    }

    @Override
    public void start() {

    }

    @Override
    public AddressContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public AddressContract.View onCreateView() {
        return AddressFragment.getInstance();
    }
}

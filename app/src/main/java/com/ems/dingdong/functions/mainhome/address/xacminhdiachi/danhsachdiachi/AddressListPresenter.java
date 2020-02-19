package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.AddressFragment;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi.ChiTietDiaChiPresenter;
import com.ems.dingdong.model.AddressListModel;

public class AddressListPresenter extends Presenter<AddressListContract.View, AddressListContract.Interactor>
        implements AddressListContract.Presenter {

    Object object;

    public AddressListPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public AddressListPresenter setObject(Object object)
    {
        this.object = object;
        return this;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public void showAddressDetail(AddressListModel addressListModel) {
        new ChiTietDiaChiPresenter(mContainerView).setChiTietDiaChi(addressListModel).pushView();
    }

    @Override
    public AddressListContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public AddressListContract.View onCreateView() {
        return new AddressListFragment().getInstance();
    }


}

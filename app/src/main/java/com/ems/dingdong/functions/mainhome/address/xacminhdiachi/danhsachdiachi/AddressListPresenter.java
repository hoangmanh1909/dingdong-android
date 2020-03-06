package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi.ChiTietDiaChiPresenter;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.timduongdi.TimDuongDiPresenter;
import com.ems.dingdong.model.AddressListModel;

public class AddressListPresenter extends Presenter<AddressListContract.View, AddressListContract.Interactor>
        implements AddressListContract.Presenter, AddressListContract.OnCloseAuthenAddress {

    Object object;
    int mType;

    public AddressListPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public AddressListPresenter setObject(Object object, int type) {
        this.object = object;
        this.mType = type;
        return this;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public void showAddressDetail(AddressListModel addressListModel) {
        if (mType == 1) {
            new ChiTietDiaChiPresenter(mContainerView).setChiTietDiaChi(addressListModel).setOnCloseListener(this).pushView();
        } else {
            new TimDuongDiPresenter(mContainerView).setChiTietDiaChi(addressListModel).pushView();
        }
    }

    @Override
    public AddressListContract.Interactor onCreateInteractor() {
        return null;
    }

    @Override
    public AddressListContract.View onCreateView() {
        return new AddressListFragment().getInstance();
    }


    @Override
    public void closeAuthorise() {
        back();
    }
}

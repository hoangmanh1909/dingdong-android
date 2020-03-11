package com.ems.dingdong.functions.mainhome.address.xacminhdiachi;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi.AddressListPresenter;
import com.ems.dingdong.utiles.Constants;

public class XacMinhDiaChiPresenter extends Presenter<XacMinhDiaChiContract.View, XacMinhDiaChiContract.Interactor>
        implements XacMinhDiaChiContract.Presenter {
    public XacMinhDiaChiPresenter(ContainerView mContainerView) {
        super(mContainerView);
    }

    @Override
    public void start() {

    }

    @Override
    public XacMinhDiaChiContract.Interactor onCreateInteractor() {
        return new XacMinhDiaChiInteractor(this);
    }

    @Override
    public XacMinhDiaChiContract.View onCreateView() {
        return XacMinhDiaChiFragment.getInstance();
    }

    @Override
    public void getLocationAddress(double longitude, double latitude) {
        new AddressListPresenter(mContainerView).setPoint(longitude, latitude).setType(Constants.TYPE_DETAIL_ADDRESS).pushView();
    }
}

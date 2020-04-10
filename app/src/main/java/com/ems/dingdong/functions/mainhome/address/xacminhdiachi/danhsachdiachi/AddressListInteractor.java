package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.network.NetWorkController;

public class AddressListInteractor extends Interactor<AddressListContract.Presenter> implements AddressListContract.Interactor {

    public AddressListInteractor(AddressListContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void vietmapSearchByAddress(String address, Double longitude, Double latitude, CommonCallback<XacMinhDiaChiResult> callback) {
        NetWorkController.vietmapSearch(address, longitude, latitude, callback);
    }

    @Override
    public void vietmapSearchByPoint(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback) {
        NetWorkController.getAddressByLocation(longitude, latitude, callback);
    }
}

package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.danhsachdiachi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.MapResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class AddressListInteractor extends Interactor<AddressListContract.Presenter> implements AddressListContract.Interactor {

    public AddressListInteractor(AddressListContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<XacMinhDiaChiResult> vietmapSearchByAddress(String address, Double longitude, Double latitude) {
        return NetWorkController.vietmapSearch(address, longitude, latitude);
    }

    @Override
    public Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude) {
        return NetWorkController.vietmapVitriEndCode(longitude,latitude);
    }

    @Override
    public void vietmapSearchByPoint(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback) {
        NetWorkController.getAddressByLocation(longitude, latitude, callback);
    }
}

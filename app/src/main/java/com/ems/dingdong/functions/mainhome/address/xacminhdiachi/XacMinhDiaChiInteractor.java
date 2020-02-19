package com.ems.dingdong.functions.mainhome.address.xacminhdiachi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.network.NetWorkController;

public class XacMinhDiaChiInteractor  extends Interactor<XacMinhDiaChiContract.Presenter>
        implements XacMinhDiaChiContract.Interactor {
    public XacMinhDiaChiInteractor(XacMinhDiaChiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getLocationAddress(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback) {
        NetWorkController.getAddressByLocation(longitude,latitude,callback);
    }
}

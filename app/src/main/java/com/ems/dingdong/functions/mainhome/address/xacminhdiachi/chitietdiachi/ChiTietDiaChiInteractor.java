package com.ems.dingdong.functions.mainhome.address.xacminhdiachi.chitietdiachi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.vietmap.UpdateRequest;
import com.ems.dingdong.network.NetWorkController;

public class ChiTietDiaChiInteractor extends Interactor<ChiTietDiaChiContract.Presenter>
        implements ChiTietDiaChiContract.Interactor {
    public ChiTietDiaChiInteractor(ChiTietDiaChiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void vietmapVerify(String id, String userId, String layer, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.vietmapVerify(id, userId, layer, commonCallback);
    }

    @Override
    public void vietmapUpdate(UpdateRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.vietmapUpdate(request, callback);
    }
}

package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.CreateOrderRequest;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.model.request.PUGetBusinessProfileRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

/**
 * The CommonObject interactor
 */
class TaoTInInteractor extends Interactor<TaoTinContract.Presenter>
        implements TaoTinContract.Interactor {

    TaoTInInteractor(TaoTinContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> getTinhThanhPho(BaseRequest request) {
        return NetWorkControllerGateWay.getTinhThanhPho(request);
    }

    @Override
    public Single<SimpleResult> getQuanHuyen(BaseRequest request) {
        return NetWorkControllerGateWay.getQuanHuyen(request);
    }

    @Override
    public Single<SimpleResult> getXaPhuong(BaseRequest request) {
        return NetWorkControllerGateWay.getXaPhuong(request);
    }

    @Override
    public Single<SimpleResult> search(PUGetBusinessProfileRequest request) {
        return NetWorkControllerGateWay.search(request);
    }

    @Override
    public Single<SimpleResult> searchdiachi(PUGetBusinessProfileRequest request) {
        return NetWorkControllerGateWay.searchdaichi(request);
    }

    @Override
    public Single<SimpleResult> themTin(CreateOrderRequest createOrderRequest) {
        return NetWorkControllerGateWay.themTin(createOrderRequest);
    }
}

package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.CreateOrderRequest;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.model.request.PUGetBusinessProfileRequest;
import com.ems.dingdong.network.NetWorkController;

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
        return NetWorkController.getTinhThanhPho(request);
    }

    @Override
    public Single<SimpleResult> getQuanHuyen(BaseRequest request) {
        return NetWorkController.getQuanHuyen(request);
    }

    @Override
    public Single<SimpleResult> getXaPhuong(BaseRequest request) {
        return NetWorkController.getXaPhuong(request);
    }

    @Override
    public Single<SimpleResult> search(PUGetBusinessProfileRequest request) {
        return NetWorkController.search(request);
    }

    @Override
    public Single<SimpleResult> searchdiachi(PUGetBusinessProfileRequest request) {
        return NetWorkController.searchdaichi(request);
    }

    @Override
    public Single<SimpleResult> themTin(CreateOrderRequest createOrderRequest) {
        return NetWorkController.themTin(createOrderRequest);
    }
}

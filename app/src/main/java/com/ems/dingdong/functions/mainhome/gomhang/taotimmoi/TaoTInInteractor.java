package com.ems.dingdong.functions.mainhome.gomhang.taotimmoi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.CreateOrderRequest;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.SimpleResult;
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
    public Single<SimpleResult> getTinhThanhPho() {
        return NetWorkController.getTinhThanhPho();
    }

    @Override
    public Single<SimpleResult> getQuanHuyen(int id) {
        return NetWorkController.getQuanHuyen(id);
    }

    @Override
    public Single<SimpleResult> getXaPhuong(int id) {
        return NetWorkController.getXaPhuong(id);
    }

    @Override
    public Single<SimpleResult> search(String request) {
        return NetWorkController.search(request);
    }

    @Override
    public Single<SimpleResult> searchdiachi(String id) {
        return NetWorkController.searchdaichi(id);
    }

    @Override
    public Single<SimpleResult> themTin(CreateOrderRequest createOrderRequest) {
        return NetWorkController.themTin(createOrderRequest);
    }
}

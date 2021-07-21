package com.ems.dingdong.functions.mainhome.phathang;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

/**
 * The PhatHang interactor
 */
class PhatHangInteractor extends Interactor<PhatHangContract.Presenter>
        implements PhatHangContract.Interactor {

    PhatHangInteractor(PhatHangContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> searchTu(String request) {
        return NetWorkController.searchTu(request);
    }
}

package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.chuaphanhuong;

import com.core.base.viper.Interactor;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.model.ComfrimCreateMode;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class ChuaPhanHuongInteractor extends Interactor<ChuaPhanHuongContract.Presenter>
        implements ChuaPhanHuongContract.Interactor {

    public ChuaPhanHuongInteractor(ChuaPhanHuongContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> searchCreate(SearchMode searchMode) {
        return NetWorkController.searchCreate(searchMode);
    }

    @Override
    public Single<SimpleResult> comfirmCreate(ComfrimCreateMode comfrimCreateMode) {
        return NetWorkController.comfirmCreate(comfrimCreateMode);
    }

    @Override
    public Single<SimpleResult> ddLapBD13Vmap(OrderCreateBD13Mode createBD13Mode) {
        return NetWorkController.getLapBangKeBD13(createBD13Mode);
    }
}

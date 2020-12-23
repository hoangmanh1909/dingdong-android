package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;

public class ChiTietHoanThanhTinTheoDiaChiInteractor extends Interactor<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> implements ChiTietHoanThanhTinTheoDiaChiContract.Interactor {


    public ChiTietHoanThanhTinTheoDiaChiInteractor(ChiTietHoanThanhTinTheoDiaChiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void postImage(String pathMedia, CommonCallback<UploadSingleResult> commonCallback) {

    }

    @Override
    public void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback) {

    }
}

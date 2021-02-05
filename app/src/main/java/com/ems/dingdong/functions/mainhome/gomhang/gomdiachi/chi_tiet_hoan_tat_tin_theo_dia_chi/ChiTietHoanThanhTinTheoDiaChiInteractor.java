package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.chi_tiet_hoan_tat_tin_theo_dia_chi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.network.NetWorkController;
import java.util.List;

public class ChiTietHoanThanhTinTheoDiaChiInteractor extends Interactor<ChiTietHoanThanhTinTheoDiaChiContract.Presenter> implements ChiTietHoanThanhTinTheoDiaChiContract.Interactor {

    public ChiTietHoanThanhTinTheoDiaChiInteractor(ChiTietHoanThanhTinTheoDiaChiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }

    @Override
    public void getReasonUnSuccess(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasonsHoanTat(commonCallback);
    }

    @Override
    public void getReasonFailure(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasonsHoanTatMiss(commonCallback);
    }

    @Override
    public void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback) {
        NetWorkController.collectAllOrderPostman(list, callback);
    }

    @Override
    public void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback) {
        NetWorkController.collectOrderPostmanCollect(hoanTatTinRequest, callback);
    }

}

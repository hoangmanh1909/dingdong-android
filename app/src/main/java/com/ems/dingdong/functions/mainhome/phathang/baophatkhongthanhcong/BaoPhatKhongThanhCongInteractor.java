package com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;

/**
 * The BaoPhatKhongThanhCong interactor
 */
class BaoPhatKhongThanhCongInteractor extends Interactor<BaoPhatKhongThanhCongContract.Presenter>
        implements BaoPhatKhongThanhCongContract.Interactor {

    BaoPhatKhongThanhCongInteractor(BaoPhatKhongThanhCongContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }


    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.getSolutionByReasonCode(code, commonCallback);
    }

    @Override
    public void checkLadingCode(String parcelCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.checkLadingCode(parcelCode, callback);
    }

    @Override
    public void pushToPNS(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkController.pushToPNSDelivery(request, callback);
    }
}

package com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

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
        NetWorkControllerGateWay.getReasons(commonCallback);
    }


    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkControllerGateWay.getSolutionByReasonCode(code, commonCallback);
    }

    @Override
    public void checkLadingCode(String parcelCode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.checkLadingCode(parcelCode, callback);
    }

    @Override
    public void pushToPNS(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.pushToPNSDelivery(request, callback);
    }
}

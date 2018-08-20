package com.vinatti.dingdong.functions.mainhome.phathang.baophatkhongthanhcong;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The BaoPhatKhongThanhCong interactor
 */
class BaoPhatKhongThanhCongInteractor extends Interactor<BaoPhatKhongThanhCongContract.Presenter>
        implements BaoPhatKhongThanhCongContract.Interactor {

    BaoPhatKhongThanhCongInteractor(BaoPhatKhongThanhCongContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }

    @Override
    public void pushToPNS(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate,
                          String deliveryTime, String receiverName, String status, String reasonCode,
                          String solutionCode, String note,String ladingPostmanID , CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode,
                deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status,
                "", "", "", note, "0",ladingPostmanID, commonCallback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkController.getSolutionByReasonCode(code, commonCallback);
    }

    @Override
    public void checkLadingCode(String parcelCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.checkLadingCode(parcelCode, callback);
    }
}

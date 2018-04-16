package com.vinatti.dingdong.functions.mainhome.phathang.detail;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The BaoPhatBangKeDetail interactor
 */
class BaoPhatBangKeDetailInteractor extends Interactor<BaoPhatBangKeDetailContract.Presenter>
        implements BaoPhatBangKeDetailContract.Interactor {

    BaoPhatBangKeDetailInteractor(BaoPhatBangKeDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getReasons(CommonCallback<ReasonResult> commonCallback) {
        NetWorkController.getReasons(commonCallback);
    }

    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String sign, String note, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, sign,note, commonCallback);
    }
}

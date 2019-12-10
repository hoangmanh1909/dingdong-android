package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The BaoPhatBangKeDetail interactor
 */
class BaoPhatOfflineDetailInteractor extends Interactor<BaoPhatOfflineDetailContract.Presenter>
        implements BaoPhatOfflineDetailContract.Interactor {

    BaoPhatOfflineDetailInteractor(BaoPhatOfflineDetailContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName,
                                String receiverIDNumber, String reasonCode, String solutionCode, String status,
                                String paymentChannel, String deliveryType, String signature,
                                String note, String amount, String routeCode, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signature,
                note,amount,routeCode, commonCallback);
    }

    @Override
    public void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate,
                                  String deliveryTime, String receiverName, String reasonCode, String solutionCode,
                                  String status, String paymentChannel, String deliveryType, String sign, String note,
                                  String amount, String ladingPostmanID, String routeCode, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                solutionCode, status, paymentChannel, deliveryType, sign,note, amount,ladingPostmanID,routeCode, commonCallback);
    }
}

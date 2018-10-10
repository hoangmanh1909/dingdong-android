package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.detail;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.network.NetWorkController;

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
                                String note, String amount, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signature,
                note,amount, commonCallback);
    }
}

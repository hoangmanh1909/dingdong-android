package com.ems.dingdong.functions.mainhome.phathang.gachno.receverpersion;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The ReceverPerson interactor
 */
class ReceverPersonInteractor extends Interactor<ReceverPersonContract.Presenter>
        implements ReceverPersonContract.Interactor {

    ReceverPersonInteractor(ReceverPersonContract.Presenter presenter) {
        super(presenter);
    }
    @Override
    public void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String collectAmount, String routeCode, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentPaypost(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note,collectAmount, routeCode, commonCallback);
    }
}

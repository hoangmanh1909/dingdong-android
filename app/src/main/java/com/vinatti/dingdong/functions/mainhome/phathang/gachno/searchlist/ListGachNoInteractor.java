package com.vinatti.dingdong.functions.mainhome.phathang.gachno.searchlist;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.GachNoResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The ListGachNo interactor
 */
class ListGachNoInteractor extends Interactor<ListGachNoContract.Presenter>
        implements ListGachNoContract.Interactor {

    ListGachNoInteractor(ListGachNoContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void deliveryGetPaypostError(CommonCallback<GachNoResult> callback) {
        NetWorkController.deliveryGetPaypostError(callback);
    }

    @Override
    public void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String collectAmount, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.paymentPaypost(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note,collectAmount, commonCallback);
    }
}

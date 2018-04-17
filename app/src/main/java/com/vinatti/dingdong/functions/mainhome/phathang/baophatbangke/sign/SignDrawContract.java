package com.vinatti.dingdong.functions.mainhome.phathang.baophatbangke.sign;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.SimpleResult;

import java.util.List;

/**
 * The SignDraw Contract
 */
interface SignDrawContract {

  interface Interactor extends IInteractor<Presenter> {
    void pushToPNSDelivery(String postmanID,
                           String ladingCode,
                           String deliveryPOCode,
                           String deliveryDate,
                           String deliveryTime,
                           String receiverName,
                           String reasonCode,
                           String solutionCode,
                           String status,
                           String paymentChannel,
                           String deliveryType,
                           String signatureCapture, CommonCallback<SimpleResult> callback);

    void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, CommonCallback<SimpleResult> commonCallback);
  }

  interface View extends PresentView<Presenter> {
    void showError(String message);

    void showSuccess();

    void showSuccessMessage(String message);

    void callAppToMpost();
  }

  interface Presenter extends IPresenter<View, Interactor> {
    List<CommonObject> getBaoPhatCommon();
    void signDataAndSubmitToPNS(String base64);

    void paymentDelivery(String base64);
  }
}




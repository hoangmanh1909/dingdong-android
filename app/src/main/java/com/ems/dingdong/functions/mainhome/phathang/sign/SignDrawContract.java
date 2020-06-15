package com.ems.dingdong.functions.mainhome.phathang.sign;


import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.SimpleResult;

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
                           String amount,
                           String fileNames,
                           String signatureCapture, String ladingPostmanID,CommonCallback<SimpleResult> callback);

    void paymentDelivery(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode, String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String amount, String fileNames, CommonCallback<SimpleResult> commonCallback);
  }

  interface View extends PresentView<Presenter> {
    void showError(String message);

    void showSuccess();

    void showSuccessMessage(String message);

    void callAppToMpost();

      void finishView();
  }

  interface Presenter extends IPresenter<View, Interactor> {
    List<CommonObject> getBaoPhatCommon();
    void signDataAndSubmitToPNS(String base64);

    void paymentDelivery(String base64);
  }
}




package com.ems.dingdong.functions.mainhome.phathang.sign;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * The SignDraw Presenter
 */
public class SignDrawPresenter extends Presenter<SignDrawContract.View, SignDrawContract.Interactor> implements SignDrawContract.Presenter {

    private List<CommonObject> mBaoPhatCommon;
    private int mCount = 0;
    private int mType;

    public SignDrawPresenter(ContainerView containerView) {
        super(containerView);
    }


    @Override
    public SignDrawContract.View onCreateView() {
        return SignDrawFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public SignDrawContract.Interactor onCreateInteractor() {
        return new SignDrawInteractor(this);
    }


    @Override
    public void signDataAndSubmitToPNS(String signatureCapture) {
        String postmanID = "";
        String mobileNumber = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        final int size = mBaoPhatCommon.size();
        mView.showProgress();
        String deliveryPOCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();
        }
        for (CommonObject item : mBaoPhatCommon) {
            String parcelCode = item.getParcelCode();
            String deliveryDate = item.getDeliveryDate();
            String deliveryTime = item.getDeliveryTime();
            String receiverName = item.getRealReceiverName();
            String receiverIDNumber = item.getReceiverIDNumber();
            final String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            String note = "";
            final String paymentChannel = item.getCurrentPaymentType();
            String deliveryType = item.getDeliveryType();
            String amount = item.getAmount();
            if (TextUtils.isEmpty(amount) || amount.equals("0")) {
                amount = item.getCollectAmount();
            }
            String ladingPostmanID = item.getiD();
            if (!TextUtils.isEmpty(item.getIsCOD())) {
                if (item.getIsCOD().toUpperCase().equals("Y")) {
                    payment(postmanID,
                            parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                            solutionCode,
                            status, paymentChannel, deliveryType, signatureCapture,
                            note, amount, item.getRouteCode(), ladingPostmanID);
                } else {
                    if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                        payment(postmanID,
                                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                                solutionCode,
                                status, paymentChannel, deliveryType, signatureCapture,
                                note, amount, item.getRouteCode(), ladingPostmanID);
                    } else {
                        pushToPNSDelivery(postmanID, parcelCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, amount, signatureCapture, item.getiD(), item.getRouteCode());
                    }
                }
            } else {
                if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                    payment(postmanID,
                            parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                            solutionCode,
                            status, paymentChannel, deliveryType, signatureCapture,
                            note, amount, item.getRouteCode(), ladingPostmanID);
                } else {
                    pushToPNSDelivery(postmanID, parcelCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, amount, signatureCapture, item.getiD(), item.getRouteCode());
                }
            }
        }

    }

    private void pushToPNSDelivery(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime,
                                   String receiverName, String reasonCode, String solutionCode, String status, final String paymentChannel,
                                   String deliveryType, String amount, String signatureCapture, String ladingPostmanID, String routeCode) {
        final int size = mBaoPhatCommon.size();
        String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        PushToPnsRequest request = new PushToPnsRequest(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode,
                solutionCode, status, paymentChannel, deliveryType, signatureCapture, "", amount, ladingPostmanID, Constants.SHIFT, routeCode, signature);
        mInteractor.pushToPNSDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mCount++;
                if (mCount == size) {
                    mView.hideProgress();
                }
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccess();
                    if (paymentChannel.equals("2")) {
                        mView.callAppToMpost();
                    } else {
                        mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                        mView.finishView();

                    }
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mCount++;
                if (mCount == size) {
                    mView.hideProgress();
                }
                mView.showError(message);
            }
        });
    }

    @Override
    public void paymentDelivery(String signatureCapture) {
        String postmanID = "";
        String mobileNumber = "";
        String deliveryPOCode = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);

            deliveryPOCode = postOffice.getCode();
        }
        mView.showProgress();

        for (CommonObject item : mBaoPhatCommon) {
            String parcelCode = item.getParcelCode();
            String deliveryDate = item.getDeliveryDate();
            String deliveryTime = item.getDeliveryTime();
            String receiverName = item.getRealReceiverName();
            String receiverIDNumber = item.getReceiverIDNumber();
            String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            String note = "";
            final String paymentChannel = item.getCurrentPaymentType();
            String deliveryType = item.getDeliveryType();
            String ladingPostmanID = item.getiD();
            if (!TextUtils.isEmpty(item.getIsCOD())) {
                if (item.getIsCOD().toUpperCase().equals("Y")) {
                    payment(postmanID,
                            parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                            solutionCode,
                            status, paymentChannel, deliveryType, signatureCapture,
                            note, item.getCollectAmount(), item.getRouteCode(), ladingPostmanID);
                } else {
                    pushToPNSDelivery(postmanID, parcelCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, item.getCollectAmount(), signatureCapture, item.getiD(), item.getRouteCode());
                }
            } else {
                pushToPNSDelivery(postmanID, parcelCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, item.getCollectAmount(), signatureCapture, item.getiD(), item.getRouteCode());
            }
        }

    }

    private void payment(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate,
                         String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode,
                         String solutionCode, String status, final String paymentChannel, String deliveryType, String signatureCapture,
                         String note, String amount, String routeCode, String ladingPostmanID) {
        final int size = mBaoPhatCommon.size();

        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        PaymentDeviveryRequest request = new PaymentDeviveryRequest(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, amount, Constants.SHIFT, routeCode, ladingPostmanID, signature);
        mInteractor.paymentDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mCount++;
                if (mCount == size) {
                    mView.hideProgress();
                }
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccess();
                    if (paymentChannel.equals("2")) {
                        try {
                            mView.callAppToMpost();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                        mView.finishView();
                    }
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mCount++;
                if (mCount == size) {
                    mView.hideProgress();
                }
                mView.showError(message);

            }
        });
    }

    public SignDrawPresenter setBaoPhatBangKe(List<CommonObject> baoPhatBangKe) {
        this.mBaoPhatCommon = baoPhatBangKe;
        return this;
    }

    @Override
    public List<CommonObject> getBaoPhatCommon() {
        return mBaoPhatCommon;
    }


    public SignDrawPresenter setType(int type) {
        mType = type;
        return this;
    }
}

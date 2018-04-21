package com.vinatti.dingdong.functions.mainhome.phathang.sign;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.eventbus.BaoPhatCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.PostOffice;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;

import org.greenrobot.eventbus.EventBus;

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
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }
        final int size = mBaoPhatCommon.size();
        mView.showProgress();
        for (CommonObject item : mBaoPhatCommon) {
            String ladingCode = item.getParcelCode();
            String deliveryPOCode;
            if (mType == Constants.TYPE_BAO_PHAT_THANH_CONG) {
                String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
                if (!posOfficeJson.isEmpty()) {
                    PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
                    deliveryPOCode = postOffice.getCode();
                } else {
                    deliveryPOCode = item.getDeliveryPOCode();
                }

            } else {
                deliveryPOCode = item.getPoCode();
            }
            String deliveryDate = item.getDeliveryDate();
            String deliveryTime = item.getDeliveryTime();
            String receiverName = item.getRealReceiverName();
            final String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            final String paymentChannel = item.getCurrentPaymentType();
            String deliveryType = item.getDeliveryType();
            mInteractor.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, signatureCapture, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
        final int size = mBaoPhatCommon.size();
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
            mInteractor.paymentDelivery(postmanID,
                    parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                    status, paymentChannel, deliveryType, signatureCapture,
                    note, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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

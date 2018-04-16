package com.vinatti.dingdong.functions.mainhome.phathang.detail.sign;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObject;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.UserInfo;
import com.vinatti.dingdong.network.NetWorkController;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;


/**
 * The SignDraw Presenter
 */
public class SignDrawPresenter extends Presenter<SignDrawContract.View, SignDrawContract.Interactor> implements SignDrawContract.Presenter {

    private CommonObject mBaoPhatBangke;

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

        String ladingCode = mBaoPhatBangke.getParcelCode();
        String deliveryPOCode = mBaoPhatBangke.getPoCode();
        String deliveryDate = mBaoPhatBangke.getDeliveryDate();
        String deliveryTime = mBaoPhatBangke.getDeliveryTime();
        String receiverName = mBaoPhatBangke.getRealReceiverName();
        final String reasonCode = "";
        String solutionCode = "";
        String status = "C14";
        final String paymentChannel = mBaoPhatBangke.getCurrentPaymentType();
        String deliveryType = mBaoPhatBangke.getDeliveryType();
        mView.showProgress();
        mInteractor.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, signatureCapture, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccess();
                    if(paymentChannel.equals("2"))
                    {
                        mView.callAppToMpost();
                    }
                    else {
                        mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                        back();
                        back();
                    }
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });

    }

    public SignDrawPresenter setBaoPhatBangKe(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    @Override
    public CommonObject getBaoPhatBangKe() {
        return mBaoPhatBangke;
    }


}

package com.ems.dingdong.functions.mainhome.phathang.gachno.receverpersion;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The ReceverPerson Presenter
 */
public class ReceverPersonPresenter extends Presenter<ReceverPersonContract.View, ReceverPersonContract.Interactor>
        implements ReceverPersonContract.Presenter {

    private List<CommonObject> mListBaoPhatGachNo;
    private int mType;
    private int mCount;

    public ReceverPersonPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ReceverPersonContract.View onCreateView() {
        return ReceverPersonFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public ReceverPersonContract.Interactor onCreateInteractor() {
        return new ReceverPersonInteractor(this);
    }

    public ReceverPersonPresenter setBaoPhatBangKe(List<CommonObject> gachNos) {
        this.mListBaoPhatGachNo = gachNos;
        return this;
    }

    @Override
    public List<CommonObject> getBaoPhatCommon() {
        return mListBaoPhatGachNo;
    }

    @Override
    public void paymentPaypos() {
        String postmanID = "";
        String mobileNumber = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        final int size = mListBaoPhatGachNo.size();
        mView.showProgress();
        String deliveryPOCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();
        }
        for (CommonObject item : mListBaoPhatGachNo) {
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
            if (!TextUtils.isEmpty(item.getIsCOD())) {
                if (item.getIsCOD().toUpperCase().equals("Y")) {
                    payment(postmanID,
                            parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                            solutionCode,
                            status, paymentChannel, deliveryType, "",
                            note, amount);
                } else {
                    if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                        payment(postmanID,
                                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                                solutionCode,
                                status, paymentChannel, deliveryType, "",
                                note, amount);
                    }
                }
            } else {
                if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                    payment(postmanID,
                            parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                            solutionCode,
                            status, paymentChannel, deliveryType, "",
                            note, amount);
                }
            }
        }
    }

    private void payment(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate,
                         String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode,
                         String solutionCode, String status, final String paymentChannel, String deliveryType, String signatureCapture,
                         String note, String amount) {
        final int size = mListBaoPhatGachNo.size();
        mInteractor.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, amount, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mCount++;
                        if (mCount == size) {
                            mView.hideProgress();
                            mView.finishView();
                        }
                        if (response.body().getErrorCode().equals("00")) {
                            mView.showSuccessToast("Cập nhật giao dịch thành công.");
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

    public ReceverPersonPresenter setType(int type) {
        this.mType = type;
        return this;
    }
}

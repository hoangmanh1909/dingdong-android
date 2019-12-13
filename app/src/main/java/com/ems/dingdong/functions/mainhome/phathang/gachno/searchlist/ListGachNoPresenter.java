package com.ems.dingdong.functions.mainhome.phathang.gachno.searchlist;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The ListGachNo Presenter
 */
public class ListGachNoPresenter extends Presenter<ListGachNoContract.View, ListGachNoContract.Interactor>
        implements ListGachNoContract.Presenter {

    private int mCount = 0;

    public ListGachNoPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ListGachNoContract.View onCreateView() {
        return ListGachNoFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        //getList();
    }

    @Override
    public void getList(String fromDate, String toDate) {
        mView.showProgress();
        mInteractor.deliveryGetPaypostError(fromDate, toDate, new CommonCallback<GachNoResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<GachNoResult> call, Response<GachNoResult> response) {
                super.onSuccess(call, response);
                if ("00".equals(response.body().getErrorCode())) {
                    mView.showData(response.body().getList());
                } else {
                    mView.showAlertDialog(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<GachNoResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog(message);
            }
        });
    }

    @Override
    public ListGachNoContract.Interactor onCreateInteractor() {
        return new ListGachNoInteractor(this);
    }

    @Override
    public void paymentPayPost(List<CommonObject> paymentPaypostError) {
        String postmanID = "";
        String mobileNumber = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        int size = paymentPaypostError.size();
        mView.showProgress();
        String deliveryPOCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();
        }
        for (CommonObject item : paymentPaypostError) {
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
            payment(postmanID,
                    parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                    solutionCode,
                    status, paymentChannel, deliveryType, "",
                    note, amount,item.getRouteCode(), size);
        }
    }

    private void payment(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate,
                         String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode,
                         String solutionCode, String status, final String paymentChannel, String deliveryType, String signatureCapture,
                         String note, String collectAmount, String routeCode, final int size) {
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        PaymentPaypostRequest request= new PaymentPaypostRequest(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, collectAmount, Constants.SHIFT, routeCode, signature);
        mInteractor.paymentPaypost(request ,new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
}

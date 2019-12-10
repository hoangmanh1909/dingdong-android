package com.ems.dingdong.functions.mainhome.phathang.gachno;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.gachno.detail.TaoGachNoDetailPresenter;
import com.ems.dingdong.functions.mainhome.phathang.gachno.receverpersion.ReceverPersonPresenter;
import com.ems.dingdong.functions.mainhome.phathang.gachno.searchlist.ListGachNoPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The TaoGachNo Presenter
 */
public class TaoGachNoPresenter extends Presenter<TaoGachNoContract.View, TaoGachNoContract.Interactor>
        implements TaoGachNoContract.Presenter {

    private int mCount = 0;

    public TaoGachNoPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public TaoGachNoContract.View onCreateView() {
        return TaoGachNoFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public TaoGachNoContract.Interactor onCreateInteractor() {
        return new TaoGachNoInteractor(this);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void searchParcelCodeDelivery(String parcelCode) {
        mView.showProgress();
        mInteractor.searchParcelCodeDelivery(parcelCode, new CommonCallback<CommonObjectResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectResult> call, Response<CommonObjectResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showData(response.body().getCommonObject());

                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<CommonObjectResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void showDetail(CommonObject commonObject, int position) {
        new TaoGachNoDetailPresenter(mContainerView).setBaoPhatBangKe(commonObject).setTypeBaoPhat(Constants.TYPE_BAO_PHAT_THANH_CONG).setPositionRow(position).pushView();
    }

    @Override
    public void pushViewConfirmAll(List<CommonObject> list) {
        new ReceverPersonPresenter(mContainerView).setBaoPhatBangKe(list).setType(Constants.TYPE_BAO_PHAT_THANH_CONG).pushView();
        /*String postmanID = "";
        String mobileNumber = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        final int size = list.size();
        mView.showProgress();
        String deliveryPOCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();
        }
        for (CommonObject item : list) {
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
                    note, amount, size);
        }*/
    }

    private void payment(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate,
                         String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode,
                         String solutionCode, String status, final String paymentChannel, String deliveryType, String signatureCapture,
                         String note, String amount,String routeCode, final int size) {
        mInteractor.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, amount,routeCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mCount++;
                        if (mCount == size) {
                            mView.hideProgress();
                        }
                        if (response.body().getErrorCode().equals("00")) {
                            mView.showSuccessMessage("Cập nhật giao dịch thành công.");
                            mView.finishView();
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

    @Override
    public void callForward(String phone, String ladingCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline,ladingCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showCallSuccess();
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });

    }

    @Override
    public void showViewList() {
        new ListGachNoPresenter(mContainerView).pushView();
    }
}

package com.ems.dingdong.functions.mainhome.phathang.gachno.detail;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatBangKeDetail Presenter
 */
public class TaoGachNoDetailPresenter extends Presenter<TaoGachNoDetailContract.View, TaoGachNoDetailContract.Interactor>
        implements TaoGachNoDetailContract.Presenter {

    private CommonObject mBaoPhatBangke;
    private int mType = 0;
    private int mPosition;
    private int mPositionRow = -1;


    public TaoGachNoDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public TaoGachNoDetailContract.View onCreateView() {
        return TaoGachNoDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public TaoGachNoDetailContract.Interactor onCreateInteractor() {
        return new TaoGachNoDetailInteractor(this);
    }

    public TaoGachNoDetailPresenter setBaoPhatBangKe(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    public TaoGachNoDetailPresenter setTypeBaoPhat(int type) {
        mType = type;
        return this;
    }

    public TaoGachNoDetailPresenter setPositionTab(int pos) {
        mPosition = pos;
        return this;
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    @Override
    public int getDeliveryType() {
        return mType;
    }

    @Override
    public int getPositionRow() {
        return mPositionRow;
    }

    @Override
    public CommonObject getBaoPhatBangke() {
        return mBaoPhatBangke;
    }


    @Override
    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<ReasonInfo> reasonInfos = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<ReasonInfo>>(){}.getType());
                    mView.getReasonsSuccess(reasonInfos);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

   /* @Override
    public void submitToPNS(String reason, String solution, String note, String sign) {
        String postmanID = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
        }
        String deliveryPOSCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOSCode = postOffice.getCode();
        }
        String ladingCode = mBaoPhatBangke.getParcelCode();
        String deliveryPOCode = !TextUtils.isEmpty(deliveryPOSCode) ? deliveryPOSCode : mBaoPhatBangke.getPoCode();
        String deliveryDate = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT6);
        String receiverName = mBaoPhatBangke.getReciverName();
        String reasonCode = reason;
        String solutionCode = solution;
        String status = "C18";
        String amount = mBaoPhatBangke.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = mBaoPhatBangke.getCollectAmount();
        }
        *//*if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
            paymentDeliveryPayPost(sign);
        }
        else {*//*
        mInteractor.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime,
                receiverName, reasonCode, solutionCode, status, "", "", sign, note, amount, mBaoPhatBangke.getiD(), new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        if (response.body().getErrorCode().equals("00")) {
                            mView.showSuccessMessage("Cập nhật giao dịch thành công.");

                        } else {
                            mView.showError(response.body().getMessage());
                        }
                    }

                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                        mView.showError(message);
                    }
                });
        //}

    }*/

    @Override
    public void callForward(String phone) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, mBaoPhatBangke.getParcelCode(), new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showCallSuccess();
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
    public void loadSolution(String code) {
        mInteractor.getSolutionByReasonCode(code, new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    //  mListSolution = response.body().getSolutionInfos();
                    ArrayList<SolutionInfo> solutionInfos = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<SolutionInfo>>(){}.getType());
                    mView.showUISolution(solutionInfos);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void paymentDeliveryNoCodPayPost(String signatureCapture) {
        String postmanID = "";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String mobileNumber = "";

        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        mView.showProgress();
        String ladingCode = mBaoPhatBangke.getParcelCode();
        String deliveryPOCode = "";
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            deliveryPOCode = postOffice.getCode();

        }
        String deliveryDate = mBaoPhatBangke.getDeliveryDate();
        String deliveryTime = mBaoPhatBangke.getDeliveryTime();
        String receiverName = mBaoPhatBangke.getRealReceiverName();
        String receiverIDNumber = mBaoPhatBangke.getReceiverIDNumber();
        final String reasonCode = "";
        String solutionCode = "";
        String status = "C14";
        String note = "";
        final String paymentChannel = mBaoPhatBangke.getPaymentChannel();
        String deliveryType = mBaoPhatBangke.getDeliveryType();
        String amount = mBaoPhatBangke.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = mBaoPhatBangke.getCollectAmount();
        }
        if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
            payment(postmanID,
                    ladingCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode,
                    solutionCode,
                    status, paymentChannel, deliveryType, "",
                    note, amount, mBaoPhatBangke.getRouteCode());
        } else {
            mView.hideProgress();
        }
    }

    private void payment(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate,
                         String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode,
                         String solutionCode, String status, final String paymentChannel, String deliveryType, String signatureCapture,
                         String note, String collectAmount, String routeCode) {
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        PaymentPaypostRequest request = new PaymentPaypostRequest(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, collectAmount, Constants.SHIFT, routeCode, signature);
        mInteractor.paymentPaypost(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccessMessage("Cập nhật giao dịch thành công.");
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
    public void paymentDeliveryPayPost(String signatureCapture) {
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
        String parcelCode = mBaoPhatBangke.getParcelCode();
        String deliveryDate = mBaoPhatBangke.getDeliveryDate();
        String deliveryTime = mBaoPhatBangke.getDeliveryTime();
        String receiverName = mBaoPhatBangke.getRealReceiverName();
        String receiverIDNumber = mBaoPhatBangke.getReceiverIDNumber();
        String reasonCode = "";
        String solutionCode = "";
        String status = "C14";
        String note = "";
        String amount = mBaoPhatBangke.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = mBaoPhatBangke.getCollectAmount();
        }
        final String paymentChannel = mBaoPhatBangke.getPaymentChannel();
        String deliveryType = mBaoPhatBangke.getDeliveryType();

        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        PaymentPaypostRequest request = new PaymentPaypostRequest(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, amount, Constants.SHIFT, mBaoPhatBangke.getRouteCode(), signature);

        mInteractor.paymentPaypost(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccessMessage("Cập nhật giao dịch thành công.");
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


    public TaoGachNoDetailPresenter setPositionRow(int position) {
        mPositionRow = position;
        return this;
    }
}

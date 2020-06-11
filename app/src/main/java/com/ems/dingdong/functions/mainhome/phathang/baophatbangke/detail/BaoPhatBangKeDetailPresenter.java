package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.detail;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The BaoPhatBangKeDetail Presenter
 */
public class BaoPhatBangKeDetailPresenter extends Presenter<BaoPhatBangKeDetailContract.View, BaoPhatBangKeDetailContract.Interactor>
        implements BaoPhatBangKeDetailContract.Presenter {

    private CommonObject mBaoPhatBangke;
    private int mType = 0;
    private int mPosition;
    private int mPositionRow = -1;
    private String mAmount = "Không có";

    public BaoPhatBangKeDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public BaoPhatBangKeDetailContract.View onCreateView() {
        return BaoPhatBangKeDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        getInquiryAmount();
    }

    private void getInquiryAmount() {
        if (mBaoPhatBangke != null) {
            mInteractor.getInquiryAmount(mBaoPhatBangke.getParcelCode(), new CommonCallback<InquiryAmountResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<InquiryAmountResult> call, Response<InquiryAmountResult> response) {
                    super.onSuccess(call, response);
                    if (response.body().getErrorCode().equals("00")) {
                        mAmount = (response.body().getAmount());
                    }
                }

                @Override
                protected void onError(Call<InquiryAmountResult> call, String message) {
                    super.onError(call, message);
                }
            });
        }
    }

    @Override
    public BaoPhatBangKeDetailContract.Interactor onCreateInteractor() {
        return new BaoPhatBangKeDetailInteractor(this);
    }

    public BaoPhatBangKeDetailPresenter setBaoPhatBangKe(CommonObject baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        return this;
    }

    public BaoPhatBangKeDetailPresenter setTypeBaoPhat(int type) {
        mType = type;
        return this;
    }

    public BaoPhatBangKeDetailPresenter setPositionTab(int pos) {
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
    public void updateMobile(String phone) {
        mView.showProgress();
        mInteractor.updateMobile(mBaoPhatBangke.getCode(), phone, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                mView.showView();
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        });
    }

    @Override
    public void postImage(String path) {
        mView.showProgress();
        mInteractor.postImage(path, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                mView.showImage(response.body().getFile());
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog(message);
                mView.deleteFile();
            }
        });
    }

    @Override
    public CommonObject getBaoPhatBangke() {
        return mBaoPhatBangke;
    }

    @Override
    public String getAmount() {
        return mAmount;
    }


    @Override
    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<ReasonResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ReasonResult> call, Response<ReasonResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.getReasonsSuccess(response.body().getReasonInfos());
                }
            }

            @Override
            protected void onError(Call<ReasonResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
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
        /*if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
            paymentDeliveryPayPost(sign);
        }
        else {*/
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

    }

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
        String ladingCode=mBaoPhatBangke.getParcelCode();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, ladingCode,new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
        mInteractor.getSolutionByReasonCode(code, new CommonCallback<SolutionResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SolutionResult> call, Response<SolutionResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    //  mListSolution = response.body().getSolutionInfos();
                    mView.showUISolution(response.body().getSolutionInfos());
                }
            }

            @Override
            protected void onError(Call<SolutionResult> call, String message) {
                super.onError(call, message);
            }
        });
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
        String fileNames = mBaoPhatBangke.getFileNames();
        final String reasonCode = "";
        String solutionCode = "";
        String status = "C14";
        final String paymentChannel = mBaoPhatBangke.getCurrentPaymentType();
        String deliveryType = mBaoPhatBangke.getDeliveryType();
        String amount = mBaoPhatBangke.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = mBaoPhatBangke.getCollectAmount();
        }
        if ("12".equals(mBaoPhatBangke.getService())) {
            paymentDelivery(signatureCapture);
        } else {
            if (sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false)) {
                paymentDelivery(signatureCapture);
            } else {
                mInteractor.pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName,
                        reasonCode, solutionCode, status, paymentChannel, deliveryType, "", amount, signatureCapture, mBaoPhatBangke.getiD(), fileNames,
                        new CommonCallback<SimpleResult>((Activity) mContainerView) {
                            @Override
                            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                                super.onSuccess(call, response);
                                if (response.body().getErrorCode().equals("00")) {

                                    if (paymentChannel.equals("2")) {
                                        mView.showSuccess();
                                        mView.callAppToMpost();
                                    } else {
                                        mView.showSuccessMessage("Cập nhật giao dịch thành công.");


                                    }
                                } else {
                                    mView.showError(response.body().getMessage());
                                }
                                mView.hideProgress();
                            }

                            @Override
                            protected void onError(Call<SimpleResult> call, String message) {
                                super.onError(call, message);
                                mView.showError(message);
                                mView.hideProgress();
                            }
                        });
            }
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
        String parcelCode = mBaoPhatBangke.getParcelCode();
        String deliveryDate = mBaoPhatBangke.getDeliveryDate();
        String deliveryTime = mBaoPhatBangke.getDeliveryTime();
        String receiverName = mBaoPhatBangke.getRealReceiverName();
        String receiverIDNumber = mBaoPhatBangke.getReceiverIDNumber();
        String fileNames = mBaoPhatBangke.getFileNames();
        String reasonCode = "";
        String solutionCode = "";
        String status = "C14";
        String note = "";
        String amount = mBaoPhatBangke.getAmount();
        if (TextUtils.isEmpty(amount) || amount.equals("0")) {
            amount = mBaoPhatBangke.getCollectAmount();
        }
        final String paymentChannel = mBaoPhatBangke.getCurrentPaymentType();
        String deliveryType = mBaoPhatBangke.getDeliveryType();
        mInteractor.paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, amount, fileNames, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onSuccess(call, response);
                        mView.hideProgress();
                        if (response.body().getErrorCode().equals("00")) {

                            if (paymentChannel.equals("2")) {
                                mView.showSuccess();
                                try {
                                    mView.callAppToMpost();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                mView.showSuccessMessage("Cập nhật giao dịch thành công.");

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


    public BaoPhatBangKeDetailPresenter setPositionRow(int position) {
        mPositionRow = position;
        return this;
    }
}

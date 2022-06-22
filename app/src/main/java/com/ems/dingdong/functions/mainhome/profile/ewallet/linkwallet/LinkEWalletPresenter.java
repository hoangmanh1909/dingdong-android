package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PayLinkConfirm;
import com.ems.dingdong.model.request.PayLinkRequest;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LinkEWalletPresenter extends Presenter<LinkEWalletContract.View, LinkEWalletContract.Interactor>
        implements LinkEWalletContract.Presenter {

    private String mobileNumber;
    private String poCode;
    private String postmanCode;
    private SharedPref pref;

    public LinkEWalletPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void getDanhSachNganHang() {
        mView.showProgress();
        mInteractor.getDanhSachNganHang()
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            DanhSachNganHangRepsone[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), DanhSachNganHangRepsone[].class);
                            List<DanhSachNganHangRepsone> list1 = Arrays.asList(list);
                            mView.showDanhSach(list1);
                            mView.hideProgress();
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void linkEWallet(int type) {
        mView.showProgress();
        PayLinkRequest request = new PayLinkRequest();
        if (TextUtils.isEmpty(mobileNumber) || !TextUtils.isEmpty(poCode) || !TextUtils.isEmpty(postmanCode)) {
            refreshUserInfo();
        }
        request.setpOCode(poCode);
        request.setAccountType(type);
        request.setPostmanCode(postmanCode);
        request.setPostmanTel(mobileNumber);
        mInteractor.linkEWallet(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (linkEWalletResult, throwable) -> {
                            if (linkEWalletResult != null &&
                                    linkEWalletResult.getErrorCode().equals("00")) {
                                if (!TextUtils.isEmpty(linkEWalletResult.getRequestId())) {
                                    pref.putString(Constants.KEY_LINK_REQUEST_ID, linkEWalletResult.getRequestId());
                                    mView.showLinkSuccess(linkEWalletResult.getMessage());
                                } else {
                                    mView.showLinkError("Không liên kết được ví điện tử");
                                }
                            } else if (linkEWalletResult != null && linkEWalletResult.getErrorCode() != null) {
                                mView.showLinkError(linkEWalletResult.getMessage());
                            } else {
                                mView.showLinkError(throwable.getMessage());
                            }
                            mView.hideProgress();
                        }
                );
    }

    @Override
    public void verifyLinkWithOtp(String requestId, String otp, int type) {
        mView.showProgress();
        PayLinkConfirm payLinkConfirm = new PayLinkConfirm();
        payLinkConfirm.setOTPCode(otp);
        payLinkConfirm.setAccountType(type);
        payLinkConfirm.setRequestId(requestId);
        payLinkConfirm.setRequestId(requestId);
        if (TextUtils.isEmpty(mobileNumber) || !TextUtils.isEmpty(poCode) || !TextUtils.isEmpty(postmanCode)) {
            refreshUserInfo();
        }
        payLinkConfirm.setPostmanTel(mobileNumber);
        payLinkConfirm.setpOCode(poCode);
        payLinkConfirm.setPostmanCode(postmanCode);
        mInteractor.verifyLinkWithOtp(payLinkConfirm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((verifyLinkOtpResult, throwable) -> {
                    if (verifyLinkOtpResult != null &&
                            verifyLinkOtpResult.getErrorCode() != null &&
                            verifyLinkOtpResult.getErrorCode().equals("00")) {
                        if (!TextUtils.isEmpty(verifyLinkOtpResult.getToken())) {
                            pref.putString(Constants.KEY_PAYMENT_TOKEN, verifyLinkOtpResult.getToken());
                            mView.showOtpSuccess(verifyLinkOtpResult.getMessage());
                        } else {
                            mView.showOtpError("Không liên kết được ví điện tử");
                        }
                    } else if (verifyLinkOtpResult != null && verifyLinkOtpResult.getErrorCode() != null) {
                        mView.showOtpError(verifyLinkOtpResult.getMessage());
                    } else {
                        mView.showOtpError(throwable.getMessage());
                    }
                    mView.hideProgress();
                });
    }

    @Override
    public String getPhoneNumber() {
        return mobileNumber;
    }

    @Override
    public String getUserIdApp() {
        return postmanCode;
    }

    @Override
    public void start() {
        refreshUserInfo();
        getDanhSachNganHang();
    }

    @Override
    public LinkEWalletContract.Interactor onCreateInteractor() {
        return new LinkEWalletInteractor(this);
    }

    @Override
    public LinkEWalletContract.View onCreateView() {
        return LinkEWalletFragment.getInstance();
    }

    private void refreshUserInfo() {
        pref = SharedPref.getInstance(getViewContext());
        String mobileNSignCode = pref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
        mobileNumber = mobileNSignCode.split(";")[0];

        String posOfficeJson = pref.getString(Constants.KEY_POST_OFFICE, "");
        String userJson = pref.getString(Constants.KEY_USER_INFO, "");
        if (!posOfficeJson.isEmpty()) {
            poCode = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class).getCode();
        }

        if (!posOfficeJson.isEmpty()) {
            postmanCode = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getUserName();
        }
    }
}

package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LinkEWalletPresenter extends Presenter<LinkEWalletContract.View, LinkEWalletContract.Interactor>
        implements LinkEWalletContract.Presenter {

    public LinkEWalletPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void getEWalletToken() {
        mInteractor.getEWalletToken("vnpcod", "vhjgQF2Epv@9pBk!8HAD7eD573Ndg6JJ")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((authPayPostResult, throwable) -> {
                    if (authPayPostResult != null &&
                            authPayPostResult.getErrorCode() != null &&
                            authPayPostResult.getErrorCode().equals("SUCCESS")) {
                        SharedPref pref = SharedPref.getInstance(getViewContext());
                        if (!TextUtils.isEmpty(authPayPostResult.getAuthPayPostResponseList().getToken())) {
                            pref.putString(Constants.KEY_AUTH_TOKEN, authPayPostResult.getAuthPayPostResponseList().getToken());
                            mView.showAuthSuccess();
                        } else {
                            mView.showAuthError("Không liên kết được ví điện tử");
                        }
                    } else if (authPayPostResult != null && authPayPostResult.getErrorCode() != null) {
                        mView.showAuthError(authPayPostResult.getMessage());
                    } else {
                        mView.showAuthError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void linkEWallet(String mobile, String userAppId) {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String authToken = pref.getString(Constants.KEY_AUTH_TOKEN, "");
        mInteractor.linkEWallet(mobile, userAppId, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (linkEWalletResult, throwable) -> {
                            if (linkEWalletResult != null &&
                                    linkEWalletResult.getErrorCode() != null &&
                                    linkEWalletResult.getErrorCode().equals("SUCCESS")) {
                                if (!TextUtils.isEmpty(linkEWalletResult.getResponse().getRequestId())) {
                                    pref.putString(Constants.KEY_REQUEST_ID, linkEWalletResult.getResponse().getRequestId());
                                    mView.showLinkSuccess(linkEWalletResult.getMessage());
                                } else {
                                    mView.showLinkError("Không liên kết được ví điện tử");
                                }
                            } else if (linkEWalletResult != null && linkEWalletResult.getErrorCode() != null) {
                                mView.showLinkError(linkEWalletResult.getMessage());
                            } else {
                                mView.showLinkError(throwable.getMessage());
                            }
                        }
                );
    }

    @Override
    public void verifyLinkWithOtp(String requestId, String otp) {
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String authToken = pref.getString(Constants.KEY_AUTH_TOKEN, "");
        mInteractor.verifyLinkWithOtp(requestId, otp, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((verifyLinkOtpResult, throwable) -> {
                    if (verifyLinkOtpResult != null &&
                            verifyLinkOtpResult.getErrorCode() != null &&
                            verifyLinkOtpResult.getErrorCode().equals("SUCCESS")) {
                        if (!TextUtils.isEmpty(verifyLinkOtpResult.getResponse().getToken())) {
                            pref.putString(Constants.KEY_PAYMENT_TOKEN, verifyLinkOtpResult.getResponse().getToken());
                            mView.showOtpSuccess(verifyLinkOtpResult.getMessage());
                        } else {
                            mView.showOtpError("Không liên kết được ví điện tử");
                        }
                    } else if (verifyLinkOtpResult != null && verifyLinkOtpResult.getErrorCode() != null) {
                        mView.showOtpError(verifyLinkOtpResult.getMessage());
                    } else {
                        mView.showOtpError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void start() {

    }

    @Override
    public LinkEWalletContract.Interactor onCreateInteractor() {
        return new LinkEWalletInteractor(this);
    }

    @Override
    public LinkEWalletContract.View onCreateView() {
        return LinkEWalletFragment.getInstance();
    }
}

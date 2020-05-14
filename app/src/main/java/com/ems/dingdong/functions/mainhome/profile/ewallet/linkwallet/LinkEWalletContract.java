package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.AuthPayPostResult;
import com.ems.dingdong.model.LinkEWalletResult;
import com.ems.dingdong.model.VerifyLinkOtpResult;

import io.reactivex.Single;

public interface LinkEWalletContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<AuthPayPostResult> getEWalletToken(String userName, String password);

        Single<LinkEWalletResult> linkEWallet(String mobile, String userAppId, String authToken);

        Single<VerifyLinkOtpResult> verifyLinkWithOtp(String requestId, String otp, String authToken);
    }

    interface View extends PresentView<Presenter> {
        void showAuthSuccess();

        void showLinkSuccess(String message);

        void showOtpSuccess(String message);

        void showAuthError(String message);

        void showLinkError(String message);

        void showOtpError(String message);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getEWalletToken();

        void linkEWallet(String mobile, String userAppId);

        void verifyLinkWithOtp(String requestId, String otp);
    }
}

package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.AuthPayPostResult;
import com.ems.dingdong.model.LinkEWalletResult;
import com.ems.dingdong.model.VerifyLinkOtpResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class LinkEWalletInteractor extends Interactor<LinkEWalletContract.Presenter>
        implements LinkEWalletContract.Interactor {

    public LinkEWalletInteractor(LinkEWalletContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<AuthPayPostResult> getEWalletToken(String userName, String password) {
        return NetWorkController.getTokenWallet(userName, password);
    }

    @Override
    public Single<LinkEWalletResult> linkEWallet(String mobile, String userAppId, String authToken) {
        return NetWorkController.linkEWallet(mobile, userAppId, authToken);
    }

    @Override
    public Single<VerifyLinkOtpResult> verifyLinkWithOtp(String requestId, String otp, String authToken) {
        return NetWorkController.verifyLinkWithOtp(requestId, otp, authToken);
    }


}

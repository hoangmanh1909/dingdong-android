package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.LinkEWalletResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VerifyLinkOtpResult;
import com.ems.dingdong.model.request.PayLinkConfirm;
import com.ems.dingdong.model.request.PayLinkRequest;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class LinkEWalletInteractor extends Interactor<LinkEWalletContract.Presenter>
        implements LinkEWalletContract.Interactor {

    public LinkEWalletInteractor(LinkEWalletContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<LinkEWalletResult> linkEWallet(PayLinkRequest payLinkRequest) {
        return NetWorkController.linkEWallet(payLinkRequest);
    }

    @Override
    public Single<SimpleResult> verifyLinkWithOtp(PayLinkConfirm payLinkConfirm) {
        return NetWorkController.verifyLinkWithOtp(payLinkConfirm);
    }


}

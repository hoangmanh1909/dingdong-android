package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.AuthPayPostResult;

import io.reactivex.Single;

public class LinkEWalletInteractor extends Interactor<LinkEWalletContract.Presenter>
        implements LinkEWalletContract.Interactor {

    public LinkEWalletInteractor(LinkEWalletContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<AuthPayPostResult> getEWalletToken(String userName, String password) {
        return null;
    }
}

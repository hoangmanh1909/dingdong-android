package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.AuthPayPostResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

/**
 * The Profile interactor
 */
class ProfileInteractor extends Interactor<ProfileContract.Presenter>
        implements ProfileContract.Interactor {

    ProfileInteractor(ProfileContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<AuthPayPostResult> getEWalletToken(String userName, String password) {
        return NetWorkController.getTokenWallet(userName, password);
    }
}

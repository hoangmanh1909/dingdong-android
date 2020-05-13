package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.AuthPayPostResult;

import io.reactivex.Single;

/**
 * The Profile Contract
 */
interface ProfileContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<AuthPayPostResult> getEWalletToken(String userName, String password);
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getEWalletToken();

        void moveToEWallet();
    }
}




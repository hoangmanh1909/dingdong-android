package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The Profile Presenter
 */
public class ProfilePresenter extends Presenter<ProfileContract.View, ProfileContract.Interactor>
        implements ProfileContract.Presenter {

    public ProfilePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public ProfileContract.View onCreateView() {
        return ProfileFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }


    @Override
    public ProfileContract.Interactor onCreateInteractor() {
        return new ProfileInteractor(this);
    }

    @Override
    public void getEWalletToken() {
        mInteractor.
                getEWalletToken("vnpcod", "vhjgQF2Epv@9pBk!8HAD7eD573Ndg6JJ")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authPayPostResult -> mView.showSuccessToast(authPayPostResult.getMessage()),
                        throwable -> mView.showErrorToast(throwable.getMessage()));
    }

    @Override
    public void moveToEWallet() {
        new EWalletPresenter(mContainerView).pushView();
    }
}

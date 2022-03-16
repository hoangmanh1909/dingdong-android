package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.lichsucuocgoi.tabcall.TabCallPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankPresenter;

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
    public void moveToEWallet() {
        new ListBankPresenter(mContainerView).pushView();
//        new EWalletPresenter(mContainerView).pushView();
    }

    @Override
    public void showLichsuCuocgoi() {
        new TabCallPresenter(mContainerView).pushView();
    }
}

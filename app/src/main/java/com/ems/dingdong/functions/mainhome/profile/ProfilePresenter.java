package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.profile.prepaid.PrepaidPresenter;

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
    public void showPrepaidView() {
        new PrepaidPresenter(mContainerView).pushView();
    }
}

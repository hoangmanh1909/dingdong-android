package com.vinatti.dingdong.functions.mainhome.setting;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

/**
 * The Setting Presenter
 */
public class SettingPresenter extends Presenter<SettingContract.View, SettingContract.Interactor>
        implements SettingContract.Presenter {

    public SettingPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public SettingContract.View onCreateView() {
        return SettingFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public SettingContract.Interactor onCreateInteractor() {
        return new SettingInteractor(this);
    }
}

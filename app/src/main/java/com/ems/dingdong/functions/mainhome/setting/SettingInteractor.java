package com.ems.dingdong.functions.mainhome.setting;

import com.core.base.viper.Interactor;

/**
 * The Setting interactor
 */
class SettingInteractor extends Interactor<SettingContract.Presenter>
        implements SettingContract.Interactor {

    SettingInteractor(SettingContract.Presenter presenter) {
        super(presenter);
    }
}

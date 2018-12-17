package com.ems.dingdong.functions.mainhome.main;

import com.core.base.viper.Interactor;

/**
 * The Home interactor
 */
class MainInteractor extends Interactor<MainContract.Presenter>
        implements MainContract.Interactor {

    MainInteractor(MainContract.Presenter presenter) {
        super(presenter);
    }
}

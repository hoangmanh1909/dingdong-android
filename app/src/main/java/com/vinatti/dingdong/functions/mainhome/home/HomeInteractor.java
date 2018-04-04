package com.vinatti.dingdong.functions.mainhome.home;

import com.core.base.viper.Interactor;

/**
 * The Home interactor
 */
class HomeInteractor extends Interactor<HomeContract.Presenter>
        implements HomeContract.Interactor {

    HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }
}

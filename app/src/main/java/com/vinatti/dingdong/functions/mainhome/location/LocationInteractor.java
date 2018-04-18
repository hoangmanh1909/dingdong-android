package com.vinatti.dingdong.functions.mainhome.location;

import com.core.base.viper.Interactor;

/**
 * The Location interactor
 */
class LocationInteractor extends Interactor<LocationContract.Presenter>
        implements LocationContract.Interactor {

    LocationInteractor(LocationContract.Presenter presenter) {
        super(presenter);
    }
}

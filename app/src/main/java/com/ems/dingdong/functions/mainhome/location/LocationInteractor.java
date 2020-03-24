package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Observable;

/**
 * The Location interactor
 */
class LocationInteractor extends Interactor<LocationContract.Presenter>
        implements LocationContract.Interactor {

    LocationInteractor(LocationContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Observable<CommonObjectResult> findLocation(String ladingCode) {
        return NetWorkController.findLocation(ladingCode);
    }
}

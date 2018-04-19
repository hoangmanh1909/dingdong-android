package com.vinatti.dingdong.functions.mainhome.location;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The Location interactor
 */
class LocationInteractor extends Interactor<LocationContract.Presenter>
        implements LocationContract.Interactor {

    LocationInteractor(LocationContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void findLocation(String ladingCode, CommonCallback<CommonObjectResult> callback) {
        NetWorkController.findLocation(ladingCode, callback);
    }
}

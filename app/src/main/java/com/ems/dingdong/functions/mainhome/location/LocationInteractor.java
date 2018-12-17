package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.network.NetWorkController;

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

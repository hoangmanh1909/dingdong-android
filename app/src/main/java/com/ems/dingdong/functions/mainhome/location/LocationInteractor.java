package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * The Location interactor
 */
class LocationInteractor extends Interactor<LocationContract.Presenter>
        implements LocationContract.Interactor {

    LocationInteractor(LocationContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Observable<CommonObjectResult> findLocation(String ladingCode, String poCode) {
        return NetWorkController.findLocation(ladingCode, poCode);
    }

    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        return NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, callback);
    }
}

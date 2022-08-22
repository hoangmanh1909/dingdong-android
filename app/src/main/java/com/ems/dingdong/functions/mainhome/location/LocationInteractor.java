package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;

import io.reactivex.Observable;
import io.reactivex.Single;
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
    public Observable<SimpleResult> findLocation(String ladingCode, String poCode) {
        return NetWorkControllerGateWay.findLocation(ladingCode, poCode);
    }    @Override

    public Single<SimpleResult> ddCall(CallLiveMode callLiveMode) {
        return NetWorkController.ddCall(callLiveMode);
    }


    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback) {
        return NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POcode, callback);
    }

    @Override
    public Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest) {
        return NetWorkController.getCallHistory(historyRequest);
    }
}

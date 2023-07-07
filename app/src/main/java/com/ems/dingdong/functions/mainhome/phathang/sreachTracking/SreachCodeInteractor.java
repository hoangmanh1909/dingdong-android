package com.ems.dingdong.functions.mainhome.phathang.sreachTracking;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;

import io.reactivex.Single;
import retrofit2.Call;

public class SreachCodeInteractor extends Interactor<SreachCodeContract.Presenter>
        implements SreachCodeContract.Interactor {

    public SreachCodeInteractor(SreachCodeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> findLocation(String ladingCode, String poCode) {
        return NetWorkControllerGateWay.findLocation(ladingCode, poCode);
    }

    @Override
    public Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest) {
        return NetWorkControllerGateWay.getCallHistory(historyRequest);
    }

    @Override
    public Single<SimpleResult> ddCall(CallLiveMode callLiveMode) {
        return NetWorkControllerGateWay.ddCall(callLiveMode);
    }


    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback) {
        return NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POcode, callback);
    }
}

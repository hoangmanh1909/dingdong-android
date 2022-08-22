package com.ems.dingdong.calls.calling;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.response.ResponseObject;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import retrofit2.Call;

public class CallingInteractor extends Interactor<CallingContract.Presenter> implements CallingContract.Interactor {
    public CallingInteractor(CallingContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        return NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber, "", "",
                ladingCode, callback);
    }
}

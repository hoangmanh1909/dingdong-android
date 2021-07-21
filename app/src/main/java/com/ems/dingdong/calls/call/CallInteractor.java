package com.ems.dingdong.calls.call;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

import retrofit2.Call;

public class CallInteractor extends Interactor<CallContract.Presenter> implements CallContract.Interactor {
    public CallInteractor(CallContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        return NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, callback);
    }
}

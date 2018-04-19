package com.vinatti.dingdong.functions.mainhome.callservice;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The CallService interactor
 */
class CallServiceInteractor extends Interactor<CallServiceContract.Presenter>
        implements CallServiceContract.Interactor {

    CallServiceInteractor(CallServiceContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                callback);
    }
}

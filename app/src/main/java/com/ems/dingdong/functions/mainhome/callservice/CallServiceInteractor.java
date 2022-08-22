package com.ems.dingdong.functions.mainhome.callservice;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

/**
 * The CallService interactor
 */
class CallServiceInteractor extends Interactor<CallServiceContract.Presenter>
        implements CallServiceContract.Interactor {

    CallServiceInteractor(CallServiceContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,ladingCode,"","",
                callback);
    }
}

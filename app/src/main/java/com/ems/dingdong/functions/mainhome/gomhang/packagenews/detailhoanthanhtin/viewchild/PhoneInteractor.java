package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

/**
 * The Phone interactor
 */
class PhoneInteractor extends Interactor<PhoneContract.Presenter>
        implements PhoneContract.Interactor {

    PhoneInteractor(PhoneContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POcode, callback);
    }

    @Override
    public void updateMobile(String code, String type, String mobileNumber, CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.updateMobile(code, type, mobileNumber, commonCallback);
    }

    @Override
    public Single<SimpleResult> ddCall(CallLiveMode callLiveMode) {
        return NetWorkController.ddCall(callLiveMode);
    }

}

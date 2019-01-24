package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The Phone interactor
 */
class PhoneInteractor extends Interactor<PhoneContract.Presenter>
        implements PhoneContract.Interactor {

    PhoneInteractor(PhoneContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                callback);
    }

    @Override
    public void updateMobile(String code, String mobileNumber, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.updateMobile(code, mobileNumber, commonCallback);
    }
}

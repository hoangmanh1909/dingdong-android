package com.vinatti.dingdong.functions.mainhome.gomhang.packagenews.detailhoanthanhtin.viewchild;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

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
}

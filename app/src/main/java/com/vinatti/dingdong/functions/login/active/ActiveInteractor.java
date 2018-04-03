package com.vinatti.dingdong.functions.login.active;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ActiveResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The Active interactor
 */
class ActiveInteractor extends Interactor<ActiveContract.Presenter>
        implements ActiveContract.Interactor {

    ActiveInteractor(ActiveContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void activeAuthorized(String mobileNumber, String activeCode, String codeDeviceActive, CommonCallback<ActiveResult> callback) {
        NetWorkController.activeAuthorized(mobileNumber, activeCode, codeDeviceActive, callback);
    }
}

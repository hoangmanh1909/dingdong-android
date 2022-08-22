package com.ems.dingdong.functions.login.active;

import com.blankj.utilcode.util.PermissionUtils;
import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ActiveResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

/**
 * The Active interactor
 */
class ActiveInteractor extends Interactor<ActiveContract.Presenter>
        implements ActiveContract.Interactor {

    ActiveInteractor(ActiveContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void activeAuthorized(String mobileNumber, String activeCode, String codeDeviceActive, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.activeAuthorized(mobileNumber, activeCode, codeDeviceActive, callback);
    }
}

package com.ems.dingdong.functions.mainhome.profile.prepaid.register;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

public class RegisterInteractor extends Interactor<RegisterContract.Presenter> implements RegisterContract.Interactor {
    public RegisterInteractor(RegisterContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void register(String name, String id, String mobileNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.registerPrepaidAccount(name, id, mobileNumber, callback);
    }
}

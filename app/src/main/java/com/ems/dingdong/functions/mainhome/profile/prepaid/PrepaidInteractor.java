package com.ems.dingdong.functions.mainhome.profile.prepaid;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.PrepaidResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

public class PrepaidInteractor extends Interactor<PrepaidContract.Presenter> implements PrepaidContract.Interactor {

    public PrepaidInteractor(PrepaidContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getPrepaidInfo(String phoneNumber, CommonCallback<PrepaidResult> callback) {
        NetWorkController.getPrepaidInfo(phoneNumber, callback);
    }

    @Override
    public void getHistory(String phoneNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.getHistory(phoneNumber, callback);
    }
}

package com.ems.dingdong.functions.mainhome.main;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The Home interactor
 */
class MainInteractor extends Interactor<MainContract.Presenter>
        implements MainContract.Interactor {

    MainInteractor(MainContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getShift(String code, CommonCallback<ShiftResult> callback) {
        NetWorkController.getPostmanShift(code, callback);
    }
}

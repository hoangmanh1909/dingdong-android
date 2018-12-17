package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The CreateBd13 interactor
 */
class CreateBd13Interactor extends Interactor<CreateBd13Contract.Presenter>
        implements CreateBd13Contract.Interactor {

    CreateBd13Interactor(CreateBd13Contract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void bD13AddNew(Bd13Create json, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.addNewBD13(json, commonCallback);
    }
}

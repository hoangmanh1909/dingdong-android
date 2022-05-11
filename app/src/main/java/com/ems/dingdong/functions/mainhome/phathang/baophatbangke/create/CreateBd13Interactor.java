package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
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

    @Override
    public void searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest, CommonCallback<SimpleResult> commonCallback) {
        NetWorkController.searchLadingCreatedBd13(objRequest,commonCallback);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, callback);
    }

    @Override
    public void updateMobile(String code,String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        NetWorkController.updateMobile(code,type, phone, simpleResultCommonCallback);
    }
}

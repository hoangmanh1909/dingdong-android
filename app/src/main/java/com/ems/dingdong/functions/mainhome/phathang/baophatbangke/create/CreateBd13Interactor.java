package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VM_POSTMAN_ROUTE;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

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
        NetWorkControllerGateWay.addNewBD13(json, commonCallback);
    }

    @Override
    public Single<DeliveryPostmanResponse> searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest) {
        return NetWorkControllerGateWay.searchLadingCreatedBd13(objRequest);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

    @Override
    public void updateMobile(String code, String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        NetWorkControllerGateWay.updateMobile(code, type, phone, simpleResultCommonCallback);
    }

    @Override
    public Single<SimpleResult> ddLapBD13Vmap(OrderCreateBD13Mode createBD13Mode) {
        return NetWorkControllerGateWay.getLapBangKeBD13(createBD13Mode);
    }

    @Override
    public Single<SimpleResult> ddXacNhanLoTrinhVmap(VM_POSTMAN_ROUTE vm_postman_route) {
        return NetWorkControllerGateWay.getXacNhanLoTrinh(vm_postman_route);

    }
}

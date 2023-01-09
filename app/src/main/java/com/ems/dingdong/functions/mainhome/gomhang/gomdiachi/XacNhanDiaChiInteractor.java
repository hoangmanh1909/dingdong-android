package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.XacMinhRespone;
import com.ems.dingdong.model.response.VerifyAddressRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

class XacNhanDiaChiInteractor extends Interactor<XacNhanDiaChiContract.Presenter> implements XacNhanDiaChiContract.Interactor {

    XacNhanDiaChiInteractor(XacNhanDiaChiContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }
    @Override
    public Single<SimpleResult> ddQueuChat(RequestQueuChat request) {
        return NetWorkControllerGateWay.ddQueuChat(request);
    }
    @Override
    public Single<SimpleResult> ddGetDichVuMpit() {
        return NetWorkControllerGateWay.ddGetDichVuMpit();
    }
//    @Override
//    public Single<CommonObjectListResult> searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate) {
//        Single<CommonObjectListResult> null;
//    }

    //    @Override
//    public Single<SimpleResult> searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate,
//                                                          String toAssignDate) {
//        return NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate);
//    }
    @Override
    public Single<VerifyAddressRespone> ddVerifyAddress(VerifyAddress verifyAddress) {
        return NetWorkControllerGateWay.vietmapddVerifyAddress(verifyAddress);
    }

    @Override
    public Single<XacMinhRespone> ddCreateVietMapRequest(CreateVietMapRequest createVietMapRequest) {
        return NetWorkControllerGateWay.vietmapddCreateVietMapRequest(createVietMapRequest);
    }

    @Override
    public Single<SimpleResult> ddSreachPhone(PhoneNumber dataRequestPayment) {
        return NetWorkControllerGateWay.ddSreachPhone(dataRequestPayment);
    }

    @Override
    public Single<XacMinhDiaChiResult> vietmapSearchViTri(Double longitude, Double latitude) {
        return NetWorkControllerGateWay.vietmapVitriEndCode(longitude,latitude);
    }
}

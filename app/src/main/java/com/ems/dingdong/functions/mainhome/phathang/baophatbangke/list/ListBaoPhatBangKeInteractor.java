package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model.RequestQueuChat;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CallTomeRequest;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.PhoneNumber;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VerifyAddress;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.XacMinhRespone;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.model.response.VerifyAddressRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.network.api.ApiService;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

/**
 * The CommonObject interactor
 */
class ListBaoPhatBangKeInteractor extends Interactor<ListBaoPhatBangKeContract.Presenter>
        implements ListBaoPhatBangKeContract.Interactor {

    ListBaoPhatBangKeInteractor(ListBaoPhatBangKeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, callback);
    }

    @Override
    public Call<DeliveryPostmanResponse> searchDeliveryPostman(String postmanID, String fromDate, String toDate, String routeCode, Integer searchType, CommonCallback<DeliveryPostmanResponse> callback) {
        return NetWorkControllerGateWay.searchDeliveryPostman(postmanID, fromDate, toDate, routeCode, searchType, callback);
    }

    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        return NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

    @Override
    public Call<SimpleResult> updateMobile(String code, String type, String phone, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        return NetWorkControllerGateWay.updateMobile(code, type, phone, simpleResultCommonCallback);
    }

    @Override
    public Call<SimpleResult> updateMobileSender(String code, String type, String phoneSender, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        return NetWorkControllerGateWay.updateMobile(code, type, phoneSender, simpleResultCommonCallback);
    }

    @Override
    public Single<SimpleResult> _phatSml(SMLRequest smlRequest) {
        return NetWorkControllerGateWay.phatSml(smlRequest);
    }

    @Override
    public Single<SimpleResult> _huySml(SMLRequest smlRequest) {
        return NetWorkControllerGateWay.huySml(smlRequest);
    }

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
        return NetWorkControllerGateWay.vietmapVitriEndCode(longitude, latitude);
    }

    @Override
    public Single<SimpleResult> ddCall(CallLiveMode callLiveMode) {
        return NetWorkControllerGateWay.ddCall(callLiveMode);
    }

    @Override
    public Single<SimpleResult> ddQueuChat(RequestQueuChat request) {
        return NetWorkControllerGateWay.ddQueuChat(request);
    }

    @Override
    public Observable<SimpleResult> ddCallToMe(CallTomeRequest request) {
        return ApiService.ddCallToMe(request);
    }
}

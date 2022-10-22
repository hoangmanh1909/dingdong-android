package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.DeliveryCheckAmountPaymentResult;
import com.ems.dingdong.model.DeliverySuccessRequest;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.DeliveryPaymentV2;
import com.ems.dingdong.model.request.DeliveryProductRequest;
import com.ems.dingdong.model.request.DeliveryUnSuccessRequest;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public class XacNhanBaoPhatInteractor extends Interactor<XacNhanBaoPhatContract.Presenter> implements XacNhanBaoPhatContract.Interactor {
    public XacNhanBaoPhatInteractor(XacNhanBaoPhatContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getXaPhuong(BaseRequest request) {
        return NetWorkControllerGateWay.getXaPhuong(request);
    }

    @Override
    public Single<SimpleResult> getQuanHuyen(BaseRequest request) {
        return NetWorkControllerGateWay.getQuanHuyen(request);
    }

    @Override
    public Single<SimpleResult> getTinhThanhPho(BaseRequest request) {
        return NetWorkControllerGateWay.getTinhThanhPho(request);
    }

    @Override
    public Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        return NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

    @Override
    public Call<SimpleResult> CallForwardEditCOD(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        return NetWorkControllerGateWay.callForwardCallCenterEdit(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

    @Override
    public void getReasons(CommonCallback<SimpleResult> commonCallback) {
        NetWorkControllerGateWay.getReasons(commonCallback);
    }

    @Override
    public void getSolutionByReasonCode(String code, CommonCallback<SolutionResult> commonCallback) {
        NetWorkControllerGateWay.getSolutionByReasonCode(code, commonCallback);
    }

    @Override
    public void postImage(String path, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageSingle(path, callback);
    }

    @Override
    public void postImageAvatar(String pathAvatar, CommonCallback<UploadSingleResult> callback) {
        NetWorkController.postImageAvatar(pathAvatar, callback);
    }

    @Override
    public void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> simpleResultCommonCallback) {
        NetWorkControllerGateWay.paymentDelivery(request, simpleResultCommonCallback);
    }

    /**
     * delivery success.
     *
     * @param request
     */
    @Override
    public Single<DeliveryCheckAmountPaymentResult> checkDeliverySuccess(DeliverySuccessRequest request) {
        return NetWorkControllerGateWay.checkDeliverySuccess(request);
    }

//    @Override
//    public Single<DeliveryCheckAmountPaymentResult> paymentDelivery(List<PaypostPaymentRequest> request) {
//        return NetWorkController.checkAmountPayment(request);
//    }

    @Override
    public Single<SimpleResult> paymentV2(DeliveryPaymentV2 request) {
        return NetWorkControllerGateWay.paymentV2(request);
    }

    /**
     * delivery not success.
     *
     * @param request
     * @param callback
     */
    @Override
    public void pushToDeliveryUnSuccess(DeliveryUnSuccessRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.pushToPNSDeliveryUnSuccess(request, callback);
    }

//    @Override
//    public void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
//        NetWorkController.pushToPNSDelivery(request, callback);
//    }

    @Override
    public void getRouteByPoCode(String poCode, CommonCallback<RouteInfoResult> callback) {
        NetWorkControllerGateWay.getDeliveryRoute(poCode, callback);
    }

    @Override
    public void getPostman(String poCode, int routeId, String routeType, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.getPostmanByRoute(poCode, routeId, routeType, callback);
    }

    @Override
    public void cancelDivided(List<DingDongCancelDividedRequest> request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.cancelDivided(request, callback);
    }

    @Override
    public void changeRouteInsert(ChangeRouteRequest requests, CommonCallback<SimpleResult> callback) {
        NetWorkController.changeRouteInsert(requests, callback);
    }

    @Override
    public void deliveryPartial(DeliveryProductRequest request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.deliveryPartial(request, callback);
    }

    @Override
    public Single<DecodeDiaChiResult> vietmapSearchDecode(String Decode) {
        return NetWorkControllerGateWay.vietmapSearchDecode(Decode);
    }
}

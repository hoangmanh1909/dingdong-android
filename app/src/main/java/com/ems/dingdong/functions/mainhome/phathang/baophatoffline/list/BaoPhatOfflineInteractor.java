package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * The BaoPhatThanhCong interactor
 */
class BaoPhatOfflineInteractor extends Interactor<BaoPhatOfflineContract.Presenter>
        implements BaoPhatOfflineContract.Interactor {

    BaoPhatOfflineInteractor(BaoPhatOfflineContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchParcelCodeDelivery(String parcelCode, String signature, CommonCallback<CommonObjectResult> callback) {
        NetWorkControllerGateWay.searchParcelCodeDelivery(parcelCode, signature, callback);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

    @Override
    public Single<SimpleResult> pushToPNSDelivery(PushToPnsRequest request) {
        return NetWorkControllerGateWay.pushToPNSDelivery(request);
    }

    @Override
    public Single<SimpleResult> paymentDelivery(PaymentDeviveryRequest request) {
        return NetWorkControllerGateWay.paymentDelivery(request);
    }

    @Override
    public Observable<UploadSingleResult> postImageObservable(String path) {
        return NetWorkController.postImageObservable(path);
    }
}

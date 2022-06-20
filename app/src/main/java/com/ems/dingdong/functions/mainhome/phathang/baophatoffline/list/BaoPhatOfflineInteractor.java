package com.ems.dingdong.functions.mainhome.phathang.baophatoffline.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;

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
    public void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback) {
        NetWorkController.searchParcelCodeDelivery(parcelCode, callback);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POCode, callback);
    }

    @Override
    public Single<SimpleResult> pushToPNSDelivery(PushToPnsRequest request) {
        return NetWorkController.pushToPNSDelivery(request);
    }

    @Override
    public Single<SimpleResult> paymentDelivery(PaymentDeviveryRequest request) {
        return NetWorkController.paymentDelivery(request);
    }

    @Override
    public Observable<UploadSingleResult> postImageObservable(String path) {
        return NetWorkController.postImageObservable(path);
    }
}

package com.vinatti.dingdong.functions.mainhome.phathang.baophatoffline.list;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.network.NetWorkController;

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
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                callback);
    }
}

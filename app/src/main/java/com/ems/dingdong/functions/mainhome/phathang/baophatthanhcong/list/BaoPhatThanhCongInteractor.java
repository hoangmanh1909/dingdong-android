package com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.list;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The BaoPhatThanhCong interactor
 */
class BaoPhatThanhCongInteractor extends Interactor<BaoPhatThanhCongContract.Presenter>
        implements BaoPhatThanhCongContract.Interactor {

    BaoPhatThanhCongInteractor(BaoPhatThanhCongContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback) {
        NetWorkController.searchParcelCodeDelivery(parcelCode, callback);
    }

    @Override
    public void callForwardCallCenter(String callerNumber, String calleeNumber, String callForwardType, String hotlineNumber, String ladingCode, String PostmanId, String POcode, CommonCallback<SimpleResult> callback) {
        NetWorkController.callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber,
                ladingCode, PostmanId, POcode, callback);
    }
}

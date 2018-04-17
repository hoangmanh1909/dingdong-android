package com.vinatti.dingdong.functions.mainhome.phathang.baophatthanhcong.list;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.network.NetWorkController;

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
}

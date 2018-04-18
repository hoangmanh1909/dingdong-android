package com.vinatti.dingdong.functions.mainhome.phathang.thongke.history;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The History interactor
 */
class HistoryInteractor extends Interactor<HistoryContract.Presenter>
        implements HistoryContract.Interactor {

    HistoryInteractor(HistoryContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getHistoryDelivery(String parcelCode, CommonCallback<CommonObjectListResult> callback) {
        NetWorkController.getHistoryDelivery(parcelCode, callback);
    }
}

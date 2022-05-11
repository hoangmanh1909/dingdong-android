package com.ems.dingdong.functions.mainhome.phathang.thongke.history;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The History interactor
 */
class HistoryInteractor extends Interactor<HistoryContract.Presenter>
        implements HistoryContract.Interactor {

    HistoryInteractor(HistoryContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getHistoryDelivery(String parcelCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.getHistoryDelivery(parcelCode, callback);
    }
}

package com.vinatti.dingdong.functions.mainhome.callservice.history;

import com.core.base.viper.Interactor;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.HistoryCallResult;
import com.vinatti.dingdong.network.NetWorkController;

/**
 * The History interactor
 */
class HistoryCallInteractor extends Interactor<HistoryCallContract.Presenter>
        implements HistoryCallContract.Interactor {

    HistoryCallInteractor(HistoryCallContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public void searchCallCenter(String postmanID, String fromDate, String toDate, CommonCallback<HistoryCallResult> callback) {
        NetWorkController.searchCallCenter(postmanID, fromDate, toDate, callback);
    }
}

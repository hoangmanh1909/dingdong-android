package com.ems.dingdong.functions.mainhome.gomhang.statistic;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticCollectResult;
import com.ems.dingdong.network.NetWorkController;

import java.util.ArrayList;

/**
 * The CommonObject interactor
 */
class ListStatisticInteractor extends Interactor<ListStatisticContract.Presenter>
        implements ListStatisticContract.Interactor {

    ListStatisticInteractor(ListStatisticContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchStatisticCollect(String postmanID,  String fromDate, String toDate, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchStatisticCollect(postmanID,  fromDate, toDate, callback);
    }

}

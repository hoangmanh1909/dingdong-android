package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.network.NetWorkController;

public class StatisticDebitDetailInteractor extends Interactor<StatisticDebitDetailContract.Presenter>
        implements StatisticDebitDetailContract.Interactor {

    public StatisticDebitDetailInteractor(StatisticDebitDetailContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void statisticDebitDetail(String postmanID, String fromDate, String toDate,
                                     String statusCode, CommonCallback<StatisticDebitDetailResult> callback) {
        NetWorkController.statisticDebitDetail(postmanID, fromDate, toDate, statusCode, callback);
    }
}

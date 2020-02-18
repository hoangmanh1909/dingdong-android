package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDebitGeneralResult;
import com.ems.dingdong.network.NetWorkController;

public class StatisticDebitInteractor extends Interactor<StatisticDebitContract.Presenter>
        implements StatisticDebitContract.Interactor {

    public StatisticDebitInteractor(StatisticDebitPresenter presenter) {
        super(presenter);
    }

    @Override
    public void getDebitStatistic(String postmanID, String fromDate, String toDate,
                                  CommonCallback<StatisticDebitGeneralResult> callback) {
        NetWorkController.statisticDebitGeneral(postmanID, fromDate, toDate, callback);
    }
}

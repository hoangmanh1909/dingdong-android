package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDebitGeneralResult;
import com.ems.dingdong.network.NetWorkController;

public class ThongKeGachNoInteractor extends Interactor<ThongKeGachNoContract.Presenter>
        implements ThongKeGachNoContract.Interactor {

    public ThongKeGachNoInteractor(ThongKeGachNoPresenter presenter) {
        super(presenter);
    }

    @Override
    public void getDebitStatistic(String postmanID, String fromDate, String toDate,
                                  CommonCallback<StatisticDebitGeneralResult> callback) {
        NetWorkController.statisticDebitGeneral(postmanID, fromDate, toDate, callback);
    }
}

package com.ems.dingdong.functions.mainhome.phathang.gachno.thongke.gachnothanhcong;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.network.NetWorkController;

public class GachNoThanhCongInteractor extends Interactor<GachNoThanhCongContract.Presenter>
        implements GachNoThanhCongContract.Interactor {

    public GachNoThanhCongInteractor(GachNoThanhCongContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void statisticDebitDetail(String postmanID, String fromDate, String toDate,
                                     String statusCode, CommonCallback<StatisticDebitDetailResult> callback) {
        NetWorkController.statisticDebitDetail(postmanID, fromDate, toDate, statusCode, callback);
    }
}

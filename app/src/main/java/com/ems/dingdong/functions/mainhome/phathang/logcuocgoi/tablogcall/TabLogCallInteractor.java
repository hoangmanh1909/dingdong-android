package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;

import io.reactivex.Single;

public class TabLogCallInteractor extends Interactor<TabLogCallContract.Presenter>
        implements TabLogCallContract.Interactor {

    public TabLogCallInteractor(TabLogCallContract.Presenter presenter) {
        super(presenter);
    }


    @Override
    public Single<SimpleResult> getHistoryCallToal(HistoryRequest historyRequest) {
        return NetWorkControllerGateWay.getCallHistoryTotal(historyRequest);
    }
}

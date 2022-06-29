package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.utiles.Log;

import io.reactivex.Single;

public class LogInteractor extends Interactor<LogContract.Presenter>
        implements LogContract.Interactor {

    public LogInteractor(LogContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest) {
        return NetWorkController.getCallHistory(historyRequest);
    }
}



package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi;

import com.core.base.viper.Interactor;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;

import io.reactivex.Single;

public class LogCallInteractor extends Interactor<LogCallContract.Presenter>
        implements LogCallContract.Interactor {

    public LogCallInteractor(LogCallContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest) {
        return NetWorkController.getCallHistory(historyRequest);
    }
}

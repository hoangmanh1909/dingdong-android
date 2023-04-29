package com.ems.dingdong.functions.mainhome.profile.trace_log;

import com.core.base.viper.Interactor;

public class TraceLogInterator extends Interactor<TraceLogContract.Presenter>
        implements TraceLogContract.Interactor {
    public TraceLogInterator(TraceLogContract.Presenter presenter) {
        super(presenter);
    }
}

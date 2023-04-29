package com.ems.dingdong.functions.mainhome.profile.trace_log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;

public class TraceLogPresenter extends Presenter<TraceLogContract.View, TraceLogContract.Interactor>
        implements TraceLogContract.Presenter {

    ;

    public TraceLogPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public TraceLogContract.Interactor onCreateInteractor() {
        return new TraceLogInterator(this);
    }

    @Override
    public TraceLogContract.View onCreateView() {
        return TraceLogFragment.getInstance();
    }
}

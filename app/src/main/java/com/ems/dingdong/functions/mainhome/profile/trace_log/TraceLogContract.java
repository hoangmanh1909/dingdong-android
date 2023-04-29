package com.ems.dingdong.functions.mainhome.profile.trace_log;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;

public interface TraceLogContract {
    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {

    }
}

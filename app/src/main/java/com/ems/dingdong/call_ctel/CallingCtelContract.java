package com.ems.dingdong.call_ctel;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.calls.calling.CallingContract;

public interface CallingCtelContract {
    interface Interactor extends IInteractor<CallingContract.Presenter> {

    }

    interface View extends PresentView<CallingContract.Presenter> {
    }

    interface Presenter extends IPresenter<CallingContract.View, CallingContract.Interactor> {

    }
}

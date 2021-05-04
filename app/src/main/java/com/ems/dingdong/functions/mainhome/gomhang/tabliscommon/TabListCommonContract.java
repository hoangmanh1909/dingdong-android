package com.ems.dingdong.functions.mainhome.gomhang.tabliscommon;

import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;

public class TabListCommonContract {
    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        ContainerView getContainerView();

        int getType();
    }
}

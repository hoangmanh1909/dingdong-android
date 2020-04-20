package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.statistic.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.response.CancelStatisticItem;

import java.util.List;

public interface CancelStatisticDetailContract {
    interface View extends PresentView<Presenter> {

    }

    interface Interactor extends IInteractor<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<CancelStatisticItem> getItems();
    }
}

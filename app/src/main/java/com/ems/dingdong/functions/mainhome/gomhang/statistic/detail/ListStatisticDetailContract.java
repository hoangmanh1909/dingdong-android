package com.ems.dingdong.functions.mainhome.gomhang.statistic.detail;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.StatisticDetailCollect;

import java.util.List;

/**
 * The CommonObject Contract
 */
interface ListStatisticDetailContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface View extends PresentView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        List<StatisticDetailCollect> getList();
    }
}




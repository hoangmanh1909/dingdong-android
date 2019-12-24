package com.ems.dingdong.functions.mainhome.gomhang.statistic.detail;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.StatisticCollectResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The CommonObject interactor
 */
class ListStatisticDetailInteractor extends Interactor<ListStatisticDetailContract.Presenter>
        implements ListStatisticDetailContract.Interactor {

    ListStatisticDetailInteractor(ListStatisticDetailContract.Presenter presenter) {
        super(presenter);
    }


}

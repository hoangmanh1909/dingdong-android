package com.ems.dingdong.functions.mainhome.home;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.network.NetWorkController;

/**
 * The Home interactor
 */
class HomeInteractor extends Interactor<HomeContract.Presenter>
        implements HomeContract.Interactor {

    HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getHomeView(String postmanCode, String routeCode, CommonCallback<HomeCollectInfoResult> callback) {
        NetWorkController.getHomeData(postmanCode, routeCode, callback);
    }
}

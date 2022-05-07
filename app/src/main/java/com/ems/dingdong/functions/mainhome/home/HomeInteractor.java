package com.ems.dingdong.functions.mainhome.home;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.ThuGomRespone;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

/**
 * The Home interactor
 */
class HomeInteractor extends Interactor<HomeContract.Presenter>
        implements HomeContract.Interactor {

    HomeInteractor(HomeContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getHomeView(String fromDate, String toDate, String postmanCode, String routeCode, CommonCallback<HomeCollectInfoResult> callback) {
        NetWorkController.getHomeData(fromDate, toDate, postmanCode, routeCode, callback);
    }

    @Override
    public Single<ThuGomRespone> getDDThugom(BalanceModel v) {
        return NetWorkController.getDDThugom(v);
    }
}

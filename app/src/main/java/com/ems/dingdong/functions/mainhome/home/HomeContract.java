package com.ems.dingdong.functions.mainhome.home;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.HomeCollectInfoResult;

import java.util.ArrayList;

/**
 * The Home Contract
 */
interface HomeContract {

    interface Interactor extends IInteractor<Presenter> {
        void getHomeView(String postmanCode,String routeCode, CommonCallback<HomeCollectInfoResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showObjectSuccess(HomeCollectInfoResult objectResult);

        void showObjectEmpty();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showViewListCreateBd13();

        void showSetting();

        void showStatistic();

        void showHoanTatNhieuTin();

        void showViewStatisticPtc(boolean isSuccess);

        void getHomeView(String postmanCode,String routeCode);
    }
}




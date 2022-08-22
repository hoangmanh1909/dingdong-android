package com.ems.dingdong.functions.mainhome.home;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.thongke.detailsuccess.StatisticType;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.HomeCollectInfo;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.ThuGomRespone;
import com.ems.dingdong.model.ThuGomResponeValue;
import com.ems.dingdong.model.response.GetMainViewResponse;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * The Home Contract
 */
interface HomeContract {

    interface Interactor extends IInteractor<Presenter> {
        void getHomeView(String fromDate, String toDate, String postmanCode, String routeCode, CommonCallback<HomeCollectInfoResult> callback);

        Single<ThuGomRespone> getDDThugom(BalanceModel v);

        void getDeliveryMainView(String fromDate, String toDate, String postmanCode, String routeCode,String funcRequest, CommonCallback<SimpleResult> callback);

        void getPickUpMainView(String fromDate, String toDate, String postmanCode, String routeCode,String funcRequest, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showObjectSuccess(HomeCollectInfoResult objectResult);

        void showObjectEmpty();

        void showThuGom(ThuGomResponeValue v);

        void showGetDeliveryMainView(GetMainViewResponse response);

        void showGetPickupMainView(GetMainViewResponse response);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showViewListCreateBd13();

        void showSetting();

        void showStatistic();

        void showHoanTatNhieuTin();

        void showViewStatisticPtc(StatisticType isSuccess);

        void getHomeView(String fromDate, String toDate, String postmanCode, String routeCode);

        void showListBd13(int typeListDelivery);

        void getDDThugom(BalanceModel v);

        void getDeliveryMainView(String fromDate, String toDate, String postmanCode, String routeCode,String funcRequest);

        void getPickUpMainView(String fromDate, String toDate, String postmanCode, String routeCode,String funcRequest);
    }
}




package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * The Setting Contract
 */
interface XacNhanConfirmContract {

    interface Interactor extends IInteractor<Presenter> {
        void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback);

        void getRouteByPoCode(String poCode, CommonCallback<RouteInfoResult> callback);

        void getPostman(String poCode, int routeId, String routeType, CommonCallback<UserInfoResult> callback);

        Single<SimpleResult> orderChangeRoute(OrderChangeRouteInsertRequest request);
    }

    interface View extends PresentView<Presenter> {
        void showError(String message);

        void showResult(ConfirmAllOrderPostman allOrderPostman);

        void showRoute(ArrayList<RouteInfo> routeInfos);

        void showPostman(ArrayList<UserInfo> userInfos);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        ArrayList<ConfirmOrderPostman> getList();

        String setTenKH();

        void confirmAllOrderPostman();

        void getRouteByPoCode(String poCode);

        void getPostman(String poCode, int routeId, String routeType);

        void orderChangeRoute(OrderChangeRouteInsertRequest request);
    }
}




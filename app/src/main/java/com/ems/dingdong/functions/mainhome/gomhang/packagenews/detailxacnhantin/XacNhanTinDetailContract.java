package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.XacNhanBaoPhatContract;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * The XacNhanTinDetail Contract
 */
interface XacNhanTinDetailContract {

    interface Interactor extends IInteractor<Presenter> {
        // void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback);

        void confirmOrderPostmanCollect(String orderPostmanID, String employeeID,
                                        String statusCode, String confirmReason, CommonCallback<SimpleResult> callback);

        void getRouteByPoCode(String poCode, CommonCallback<SimpleResult> callback);

        void getPostman(String poCode, int routeId, String routeType, CommonCallback<SimpleResult> callback);

        Single<SimpleResult> orderChangeRoute(OrderChangeRouteInsertRequest request);
    }

    interface View extends PresentView<Presenter> {
        //  void showErrorAndBack(String message);

        void showView(CommonObject commonObject);

        void showMessage(String message);

        void showError(String message);

        void showRoute(ArrayList<RouteInfo> routeInfos);

        void showPostman(ArrayList<UserInfo> userInfos);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, String statusCode, String reason);

        String getMode();

        // void searchOrderPostman();
        CommonObject getCommonObject();

        void getRouteByPoCode(String poCode);

        void getPostman(String poCode, int routeId, String routeType);

        void orderChangeRoute(OrderChangeRouteInsertRequest request);
    }
}




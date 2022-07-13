package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import io.reactivex.Single;

/**
 * The XacNhanTinDetail interactor
 */
class XacNhanTinDetailInteractor extends Interactor<XacNhanTinDetailContract.Presenter>
        implements XacNhanTinDetailContract.Interactor {

    XacNhanTinDetailInteractor(XacNhanTinDetailContract.Presenter presenter) {
        super(presenter);
    }

  /*  @Override
    public void searchOrderPostmanCollect(String orderPostmanID, String orderID, String postmanID, String status, String fromAssignDate, String toAssignDate, CommonCallback<CommonObjectListResult> commonCallback) {
        NetWorkController.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, commonCallback);
    }*/

    @Override
    public void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, String statusCode, String confirmReason, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.confirmOrderPostmanCollect(orderPostmanID, employeeID, statusCode, confirmReason, callback);
    }

    @Override
    public void getRouteByPoCode(String poCode, CommonCallback<RouteInfoResult> callback) {
        NetWorkController.getRoute(poCode, callback);
    }

    @Override
    public void getPostman(String poCode, int routeId, String routeType, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.getPostmanByRoute(poCode, routeId, routeType, callback);
    }

    @Override
    public Single<SimpleResult> orderChangeRoute(OrderChangeRouteInsertRequest request) {
        return NetWorkControllerGateWay.orderChangeRoute(request);
    }

}

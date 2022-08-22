package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * The Setting interactor
 */
class XacNhanConfirmInteractor extends Interactor<XacNhanConfirmContract.Presenter>
        implements XacNhanConfirmContract.Interactor {

    XacNhanConfirmInteractor(XacNhanConfirmContract.Presenter presenter) {
        super(presenter);
    }
    @Override
    public void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.confirmAllOrderPostman(request, callback);
    }

    @Override
    public void getRouteByPoCode(String poCode, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.getRoute(poCode, callback);
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

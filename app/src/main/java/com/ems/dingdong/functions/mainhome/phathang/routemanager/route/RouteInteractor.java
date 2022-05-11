package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.OrderChangeRouteRequest;
import com.ems.dingdong.model.request.OrderChangeRouteDingDongManagementRequest;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

public class RouteInteractor extends Interactor<RouteConstract.Presenter> implements RouteConstract.Interactor {
    public RouteInteractor(RouteConstract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public Single<SimpleResult> getChangeRouteOrder(OrderChangeRouteDingDongManagementRequest request) {
        return NetWorkController.getChangeRouteOrder(request);
    }

    @Override
    public Single<SimpleResult> cancelOrder(OrderChangeRouteRequest request) {
        return NetWorkController.cancelOrder(request);
    }

    @Override
    public Single<SimpleResult> approveOrder(OrderChangeRouteRequest request) {
        return NetWorkController.approveOrder(request);
    }

    @Override
    public Single<SimpleResult> rejectOrder(OrderChangeRouteRequest request) {
        return NetWorkController.rejectOrder(request);
    }

    @Override
    public void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, String statusCode, Integer fromRouteId, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchForApproved(ladingCode, fromDate, toDate, postmanId, routeId, pooCode, statusCode, fromRouteId, callback);
    }

    @Override
    public void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, String statusCode, Integer fromRouteId, CommonCallback<SimpleResult> callback) {
        NetWorkController.searchForCancel(ladingCode, fromDate, toDate, postmanId, routeId, pooCode, statusCode, fromRouteId, callback);
    }

    @Override
    public void approvedAgree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.approvedAgree(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode, callback);
    }

    @Override
    public void approvedDisagree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.approvedDisagree(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode, callback);
    }

    @Override
    public void cancel(Integer id, Integer postmanId, CommonCallback<SimpleResult> callback) {
        NetWorkController.cancelRoute(id, postmanId, callback);
    }

    @Override
    public void getRouteByPoCode(String poCode, CommonCallback<SimpleResult> callback) {
        NetWorkController.getDeliveryRoute(poCode, callback);
    }
}

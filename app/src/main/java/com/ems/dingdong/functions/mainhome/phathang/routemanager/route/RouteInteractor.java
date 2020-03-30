package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.network.NetWorkController;

public class RouteInteractor extends Interactor<RouteConstract.Presenter> implements RouteConstract.Interactor {
    public RouteInteractor(RouteConstract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, String statusCode, Integer fromRouteId, CommonCallback<RouteResult> callback) {
        NetWorkController.searchForApproved(ladingCode, fromDate, toDate, postmanId, routeId, pooCode, statusCode, fromRouteId, callback);
    }

    @Override
    public void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String pooCode, String statusCode, Integer fromRouteId, CommonCallback<RouteResult> callback) {
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
    public void getRouteByPoCode(String poCode, CommonCallback<RouteInfoResult> callback) {
        NetWorkController.getDeliveryRoute(poCode, callback);
    }
}

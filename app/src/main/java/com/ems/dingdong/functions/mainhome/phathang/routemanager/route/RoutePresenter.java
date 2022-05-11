package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import android.content.Context;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin.XacNhanTinDetailPresenter;
import com.ems.dingdong.functions.mainhome.phathang.routemanager.RouteTabsConstract;
import com.ems.dingdong.functions.mainhome.phathang.routemanager.route.detail.DetailRouteChangePresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.OrderChangeRouteModel;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.OrderChangeRouteRequest;
import com.ems.dingdong.model.request.OrderChangeRouteDingDongManagementRequest;
import com.ems.dingdong.model.response.OrderChangeRouteDingDongManagementResponse;
import com.ems.dingdong.model.response.RouteResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class RoutePresenter extends Presenter<RouteConstract.View, RouteConstract.Interactor> implements RouteConstract.Presenter {
    private RouteInfo routeInfo;
    private UserInfo userInfo;
    private PostOffice postOffice;
    Calendar calCreate;
    private int typeRoute;
    String mode;

    private RouteTabsConstract.OnTabsListener titleTabsListener;

    public RoutePresenter(ContainerView containerView, String mode) {
        super(containerView);
        this.mode = mode;
    }

    @Override
    public void start() {

    }

    @Override
    public String getMode() {
        return mode;
    }

    @Override
    public void getChangeRouteOrder(String fromDate, String toDate) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

        OrderChangeRouteDingDongManagementRequest request = new OrderChangeRouteDingDongManagementRequest();
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setPostmanCode(userInfo.getUserName());
        request.setRouteCode(routeInfo.getRouteCode());
        request.setPOCode(postOffice.getCode());

        mView.hideProgress();
        mInteractor.getChangeRouteOrder(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        OrderChangeRouteDingDongManagementResponse managementResponse = NetWorkController.getGson().fromJson(simpleResult.getData(), OrderChangeRouteDingDongManagementResponse.class);
                        mView.showListOrderSucces(managementResponse);
                        if (titleTabsListener != null) {
                            if (typeRoute == Constants.ROUTE_RECEIVED)
                                titleTabsListener.setQuantity(managementResponse.getToOrders().size(), typeRoute);
                            else
                                titleTabsListener.setQuantity(managementResponse.getFromOrders().size(), typeRoute);
                        }
                    }
                }, throwable -> {
                    mView.hideProgress();
                    mView.showErrorToast(throwable.getMessage());
                });
    }

    @Override
    public void showDetailOrder(OrderChangeRouteModel item) {
        CommonObject commonObject = new CommonObject();
        commonObject.setAssignDateTime(item.getDivideDate());
        commonObject.setAssignFullName(item.getDivideByName());
        commonObject.setReceiverAddress(item.getContactAddress());
        commonObject.setReceiverName(item.getContactName());
        commonObject.setReceiverPhone(item.getContactPhone());
        commonObject.setDescription(item.getContents());
        commonObject.setQuantity(Integer.toString(item.getQuantity()));
        commonObject.setWeigh(Integer.toString(item.getWeight()));
        commonObject.setCode(item.getOrderCode());
//        commonObject.setDivideByName(item.getDivideByName());
        commonObject.setCustomerName(item.getCustomerName());
        new XacNhanTinDetailPresenter(mContainerView).setCommonObject(commonObject).setMode("VIEW").pushView();
    }

    public RoutePresenter setTypeRoute(int typeRoute) {
        this.typeRoute = typeRoute;
        return this;
    }

    public RoutePresenter setTitleTabsListener(RouteTabsConstract.OnTabsListener titleTabsListener) {
        this.titleTabsListener = titleTabsListener;
        return this;
    }

    @Override
    public RouteConstract.Interactor onCreateInteractor() {
        return new RouteInteractor(this);
    }

    @Override
    public RouteConstract.View onCreateView() {
        return RouteFragment.getInstance();
    }

    @Override
    public void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode, String statusCode, Integer fromRouteId) {
        mView.showProgress();
        mInteractor.searchForApproved(ladingCode, fromDate,
                toDate, postmanId,
                routeId, poCode, statusCode, fromRouteId, new CommonCallback<SimpleResult>(getViewContext()) {
                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                        mView.showListError(message);
                        mView.hideProgress();
                    }

                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onResponse(call, response);
                        mView.hideProgress();
                        assert response.body() != null;
                        if (response.body().getErrorCode().equals("00")) {
                            ArrayList<RouteResponse> routeResponses = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<RouteResponse>>(){}.getType());
                            mView.showListSucces(routeResponses);
                            if (titleTabsListener != null)
                                titleTabsListener.setQuantity(routeResponses.size(), typeRoute);
                        } else {
                            mView.showListError(response.body().getMessage());
                        }
                    }
                });
    }

    @Override
    public void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode, String statusCode, Integer fromRouteId) {
        mView.showProgress();
        mInteractor.searchForCancel(ladingCode, fromDate,
                toDate, postmanId,
                routeId, poCode, statusCode, fromRouteId, new CommonCallback<SimpleResult>(getViewContext()) {
                    @Override
                    protected void onError(Call<SimpleResult> call, String message) {
                        super.onError(call, message);
                        mView.showErrorToast(message);
                        mView.hideProgress();
                    }

                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        super.onResponse(call, response);
                        mView.hideProgress();
                        assert response.body() != null;
                        if (response.body().getErrorCode().equals("00")) {
                            ArrayList<RouteResponse> routeResponses = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<RouteResponse>>(){}.getType());
                            mView.showListSucces(routeResponses);
                            if (titleTabsListener != null) {
                                titleTabsListener.setQuantity(routeResponses.size(), typeRoute);
                            }
                        }
                    }
                });
    }

    @Override
    public int getTypeRoute() {
        return typeRoute;
    }

    @Override
    public void approvedAgree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode) {
        mView.showProgress();
        mInteractor.approvedAgree(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode, new CommonCallback<SimpleResult>(getViewContext()) {
            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showErrorToast(message);
                mView.hideProgress();
            }

            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onResponse(call, response);
                mView.hideProgress();
                assert response.body() != null;
                mView.showSuccessToast(response.body().getMessage());
                if (response.body().getErrorCode().equals("00")) {
                    mView.showChangeRouteCommandSucces();
                }
            }
        });
    }

    @Override
    public void approveOrder(OrderChangeRouteRequest request) {
        mView.showProgress();
        mInteractor.approveOrder(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    mView.hideProgress();
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showChangeRouteCommandSucces();
                        mView.showSuccessToast(simpleResult.getMessage());
                    }
                }, throwable -> {
                    mView.hideProgress();
                    mView.showErrorToast(throwable.getMessage());
                });
    }

    @Override
    public void approvedDisagree(String id, String ladingCode, String postmanId, String postmanCode, String poCode, String routeId, String routeCode) {
        mView.showProgress();
        mInteractor.approvedDisagree(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode, new CommonCallback<SimpleResult>(getViewContext()) {
            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showErrorToast(message);
                mView.hideProgress();
            }

            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onResponse(call, response);
                mView.hideProgress();
                assert response.body() != null;
                mView.showSuccessToast(response.body().getMessage());
                if (response.body().getErrorCode().equals("00")) {
                    mView.showChangeRouteCommandSucces();
                }
            }
        });
    }

    @Override
    public void rejectOrder(OrderChangeRouteRequest request) {
        mView.showProgress();
        mInteractor.rejectOrder(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    mView.hideProgress();
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showChangeRouteCommandSucces();
                        mView.showSuccessToast(simpleResult.getMessage());
                    }
                }, throwable -> {
                    mView.hideProgress();
                    mView.showErrorToast(throwable.getMessage());
                });
    }

    @Override
    public void cancel(Integer id, Integer postmanId) {
        mView.showProgress();
        mInteractor.cancel(id, postmanId, new CommonCallback<SimpleResult>(getViewContext()) {
            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showErrorToast(message);
                mView.hideProgress();
            }

            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onResponse(call, response);
                mView.hideProgress();
                assert response.body() != null;
                if (response.body().getErrorCode().equals("00")) {
                    mView.showChangeRouteCommandSucces();
                    mView.showSuccessToast(response.body().getMessage());
                }
            }
        });
    }

    @Override
    public void cancelOrder(OrderChangeRouteRequest request) {
        mView.showProgress();
        mInteractor.cancelOrder(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    mView.hideProgress();
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showChangeRouteCommandSucces();
                        mView.showSuccessToast(simpleResult.getMessage());
                    }
                }, throwable -> {
                    mView.hideProgress();
                    mView.showErrorToast(throwable.getMessage());
                });
    }

    @Override
    public void setTitleTab(int quantity) {

    }

    @Override
    public void showDetail(String ladingCode) {
        new DetailRouteChangePresenter(mContainerView).setLadingCode(ladingCode).pushView();
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void getRouteByPoCode(String poCode) {
        mInteractor.getRouteByPoCode(poCode, new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                assert response.body() != null;
                ArrayList<RouteInfo> arrayList = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<RouteInfo>>(){}.getType());
                mView.showRoute(arrayList);
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }
}

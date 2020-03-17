package com.ems.dingdong.functions.mainhome.phathang.routemanager.route;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryConstract;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SimpleResult;

import retrofit2.Call;
import retrofit2.Response;

public class RoutePresenter extends Presenter<RouteConstract.View, RouteConstract.Interactor> implements RouteConstract.Presenter {

    private int typeRoute;

    private ListDeliveryConstract.OnTitleTabsListener titleTabsListener;

    public RoutePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    public RoutePresenter setTypeRoute(int typeRoute) {
        this.typeRoute = typeRoute;
        return this;
    }

    public RoutePresenter setTitleTabsListener(ListDeliveryConstract.OnTitleTabsListener titleTabsListener) {
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
    public void searchForApproved(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode) {
        mInteractor.searchForApproved(ladingCode, fromDate,
                toDate, postmanId,
                routeId, poCode, new CommonCallback<RouteResult>(getViewContext()) {
                    @Override
                    protected void onError(Call<RouteResult> call, String message) {
                        super.onError(call, message);
                        mView.showErrorToast(message);
                    }

                    @Override
                    public void onResponse(Call<RouteResult> call, Response<RouteResult> response) {
                        super.onResponse(call, response);
                        if (response.body().getErrorCode().equals("00")) {
                            mView.showListSucces(response.body().getRouteResponses());
                            titleTabsListener.setQuantity(response.body().getRouteResponses().size(), typeRoute);
                        }
                    }
                });
    }

    @Override
    public void searchForCancel(String ladingCode, String fromDate, String toDate, String postmanId, String routeId, String poCode) {
        mInteractor.searchForCancel(ladingCode, fromDate,
                toDate, postmanId,
                routeId, poCode, new CommonCallback<RouteResult>(getViewContext()) {
                    @Override
                    protected void onError(Call<RouteResult> call, String message) {
                        super.onError(call, message);
                        mView.showErrorToast(message);
                    }

                    @Override
                    public void onResponse(Call<RouteResult> call, Response<RouteResult> response) {
                        super.onResponse(call, response);
                        if (response.body().getErrorCode().equals("00")) {
                            mView.showListSucces(response.body().getRouteResponses());
                            titleTabsListener.setQuantity(response.body().getRouteResponses().size(), typeRoute);
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
                if (response.body().getErrorCode().equals("00")) {
                    mView.showChangeRouteCommandSucces();
                    mView.showSuccessToast(response.body().getMessage());
                }
            }
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
                if (response.body().getErrorCode().equals("00")) {
                    mView.showChangeRouteCommandSucces();
                    mView.showSuccessToast(response.body().getMessage());
                }
            }
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
                if (response.body().getErrorCode().equals("00")) {
                    mView.showChangeRouteCommandSucces();
                    mView.showSuccessToast(response.body().getMessage());
                }
            }
        });
    }

    @Override
    public void setTitleTab(int quantity) {

    }


}

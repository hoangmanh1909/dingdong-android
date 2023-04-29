package com.ems.dingdong.functions.mainhome.gomhang.packagenews.detailxacnhantin;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;
import com.ems.dingdong.model.response.StatisticSMLDeliveryFailResponse;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The XacNhanTinDetail Presenter
 */
public class XacNhanTinDetailPresenter extends Presenter<XacNhanTinDetailContract.View, XacNhanTinDetailContract.Interactor>
        implements XacNhanTinDetailContract.Presenter {

    String mode = "ADD";

    private CommonObject commonObject;

    public XacNhanTinDetailPresenter(ContainerView containerView) {
        super(containerView);
    }

    public XacNhanTinDetailPresenter setMode(String mode) {
        this.mode = mode;
        return this;
    }

    @Override
    public XacNhanTinDetailContract.View onCreateView() {
        return XacNhanTinDetailFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        //   searchOrderPostman();
    }

    @Override
    public XacNhanTinDetailContract.Interactor onCreateInteractor() {
        return new XacNhanTinDetailInteractor(this);
    }

   /* @Override
    public void searchOrderPostman() {
        String orderPostmanID = commonObject.getOrderPostmanID();
        String orderID = "0";
        String postmanID = "0";
        String status = "";
        String fromAssignDate = "0";
        String toAssignDate = "0";
        mView.showProgress();
        mInteractor.searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate, new CommonCallback<CommonObjectListResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<CommonObjectListResult> call, Response<CommonObjectListResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    if (response.body().getList() != null) {

                        if (!response.body().getList().isEmpty()) {
                            mView.showView(response.body().getList().get(0));
                        } else {
                            mView.showErrorAndBack(response.body().getMessage());
                        }
                    } else {
                        mView.showErrorAndBack(response.body().getMessage());
                    }
                } else {
                    mView.showErrorAndBack(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<CommonObjectListResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorAndBack(message);
            }
        });
    }*/

    @Override
    public CommonObject getCommonObject() {
        return commonObject;
    }

    @Override
    public void getRouteByPoCode(String poCode) {
        mInteractor.getRouteByPoCode(poCode, new CommonCallback<RouteInfoResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<RouteInfoResult> call, Response<RouteInfoResult> response) {
                super.onSuccess(call, response);
//                ArrayList<RouteInfo> routeInfos = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken< List<RouteInfo>>(){}.getType());
                try {
                    mView.showRoute(response.body().getRouteInfos());
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            protected void onError(Call<RouteInfoResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void getPostman(String poCode, int routeId, String routeType) {
        mInteractor.getPostman(poCode, routeId, routeType, new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                ArrayList<UserInfo> userInfos = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<UserInfo>>() {
                }.getType());
                mView.showPostman(userInfos);
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void orderChangeRoute(OrderChangeRouteInsertRequest request) {
        mView.showProgress();
        mInteractor.orderChangeRoute(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showMessage(simpleResult.getMessage());
                    } else {
                        mView.showErrorToast(simpleResult.getMessage());
                    }
                }, throwable -> {
                    mView.hideProgress();
                    new ApiDisposable(throwable, getViewContext());
                });
    }

    public XacNhanTinDetailPresenter setCommonObject(CommonObject commonObject) {
        this.commonObject = commonObject;
        return this;
    }

    @Override
    public void confirmOrderPostmanCollect(String orderPostmanID, String employeeID, final String statusCode, String reason) {
        mView.showProgress();
        mInteractor.confirmOrderPostmanCollect(orderPostmanID, employeeID, statusCode, reason, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    if (statusCode.equals("P1")) {
                        mView.showMessage("Xác nhận tin thành công.");
                    } else {
                        mView.showMessage("Từ chối tin thành công.");
                    }
                } else {
                    mView.showError(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showError(message);
            }
        });
    }

    @Override
    public String getMode() {
        return mode;
    }
}

package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;
import com.ems.dingdong.network.NetWorkController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The Setting Presenter
 */
public class XacNhanConfirmPresenter extends Presenter<XacNhanConfirmContract.View, XacNhanConfirmContract.Interactor>
        implements XacNhanConfirmContract.Presenter {

    ArrayList<ConfirmOrderPostman> mListRequest;
    String tenKH;

    public XacNhanConfirmPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public XacNhanConfirmContract.View onCreateView() {
        return XacNhanConfirmFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public XacNhanConfirmContract.Interactor onCreateInteractor() {
        return new XacNhanConfirmInteractor(this);
    }

    @Override
    public ArrayList<ConfirmOrderPostman> getList() {
        return mListRequest;
    }

    @Override
    public String setTenKH() {
        return tenKH;
    }

    @Override
    public void confirmAllOrderPostman() {
        mView.showProgress();
        // check log
        mInteractor.confirmAllOrderPostman(mListRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ConfirmAllOrderPostman confirmAllOrderPostman = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<ConfirmAllOrderPostman>(){}.getType());
                    mView.showResult(confirmAllOrderPostman);
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

    public XacNhanConfirmPresenter setListRequest(ArrayList<ConfirmOrderPostman> listRequest) {
        this.mListRequest = listRequest;
        return this;
    }

    public XacNhanConfirmPresenter setTenKH(String tenKH) {
        this.tenKH = tenKH;
        return this;
    }


    @Override
    public void getRouteByPoCode(String poCode) {
        mInteractor.getRouteByPoCode(poCode, new CommonCallback<RouteInfoResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<RouteInfoResult> call, Response<RouteInfoResult> response) {
                super.onSuccess(call, response);
                mView.showRoute(response.body().getRouteInfos());
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
                ArrayList<UserInfo> userInfos = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<UserInfo>>(){}.getType());
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
                        new SweetAlertDialog(getViewContext(), SweetAlertDialog.NORMAL_TYPE)
                                .setConfirmText("OK")
                                .setTitleText("Thông báo")
                                .setContentText(simpleResult.getMessage())
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismiss();
                                    back();
                                }).show();
                    } else {
                        mView.showErrorToast(simpleResult.getMessage());
                    }
                }, throwable -> {
                    mView.hideProgress();
                    mView.showErrorToast(throwable.getMessage());
                });
    }

}

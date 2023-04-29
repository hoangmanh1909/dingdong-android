package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.dialog.IOSDialog;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.VM_POSTMAN_ROUTE;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.response.ChuaPhanHuongMode;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The CreateBd13 Presenter
 */
public class CreateBd13Presenter extends Presenter<CreateBd13Contract.View, CreateBd13Contract.Interactor>
        implements CreateBd13Contract.Presenter {


    private CreateBd13Contract.OnTabListener tabListener;

    public CreateBd13Presenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public CreateBd13Contract.View onCreateView() {
        return CreateBd13Fragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public CreateBd13Contract.Interactor onCreateInteractor() {
        return new CreateBd13Interactor(this);
    }

    @Override
    public void ddLapBD13Vmap(OrderCreateBD13Mode createBD13Mode) {
        try {
            mView.showProgress();
            mInteractor.ddLapBD13Vmap(createBD13Mode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            VietMapOrderCreateBD13DataRequest[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), VietMapOrderCreateBD13DataRequest[].class);
                            List<VietMapOrderCreateBD13DataRequest> request = Arrays.asList(list);
                            mView.showVmap(request);
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.hideProgress();

                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void postBD13AddNew(Bd13Create bd13Create,int type) {
        mView.showProgress();
        mInteractor.bD13AddNew(bd13Create, new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showSuccessMessage(response.body().getMessage(),type);
                    } else {
//                        mView.showErrorToast(response.body().getMessage());
                        new IOSDialog.Builder(getViewContext())
                                .setCancelable(false).setMessage(response.body().getMessage())
                                .setNegativeButton("Đóng", null).show();
                    }
                } else Toast.showToast(getViewContext(), "Dữ liệu trả về sai cấu trúc");
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                new IOSDialog.Builder(getViewContext())
                        .setMessage(message)
                        .setNegativeButton("Đóng", null).show();
            }
        });
    }

    @Override
    public void searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest) {
        mView.showProgress();
        mInteractor.searchLadingBd13(objRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
//                    new CommonCallback<DeliveryPostmanResponse>((Context) mContainerView) {
//                        @Override
//                        protected void onSuccess(Call<DeliveryPostmanResponse> call, Response<DeliveryPostmanResponse> response) {
//                            super.onSuccess(call, response);
//                            mView.hideProgress();
                    if (simpleResult.getErrorCode().equals("00")) {
//                                ArrayList<DeliveryPostman> deliveryPostmens = NetWorkController.getGson().fromJson(simpleResult.getData(), new TypeToken<List<DeliveryPostman>>() {
//                                }.getType());
                        mView.showListSuccess(simpleResult.getDeliveryPostmens());
                        mView.hideProgress();
                    } else {
                        mView.hideProgress();
                        new IOSDialog.Builder(getViewContext())
                                .setCancelable(false).setMessage(simpleResult.getMessage())
                                .setNegativeButton("Đóng", null).show();
                    }
//                        }
//
//                        @Override
//                        protected void onError(Call<DeliveryPostmanResponse> call, String message) {
//                            super.onError(call, message);
//                            mView.hideProgress();
//                            mView.showErrorToast(message);
//                        }
//                    });
                }, throwable -> {
                    new ApiDisposable(throwable, getViewContext());
                    mView.hideProgress();
                });
    }

    @Override
    public void callForward(String phone, String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, userInfo.getiD(), poCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showCallSuccess();
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
    public void updateMobile(String phone, String parcelCode) {
        mView.showProgress();
        mInteractor.updateMobile(parcelCode, "1", phone, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                mView.showView();
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
            }
        });
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public void onCanceled() {
        tabListener.onCanceledDelivery();
    }

    @Override
    public void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList) {

    }

    @Override
    public void titleChanged(int quantity, int currentSetTab) {
        tabListener.onQuantityChange(quantity, currentSetTab);
    }

    @Override
    public int getCurrentTab() {
        return tabListener.getCurrentTab();
    }

    public CreateBd13Presenter setOnTabListener(CreateBd13Contract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }

    @SuppressLint("CheckResult")
    @Override
    public void ddXacNhanLoTrinh(VM_POSTMAN_ROUTE vm_postman_route) {
        try {
            mView.showProgress();
            mInteractor.ddXacNhanLoTrinhVmap(vm_postman_route)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.hideProgress();
                        } else {
                            mView.hideProgress();
                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

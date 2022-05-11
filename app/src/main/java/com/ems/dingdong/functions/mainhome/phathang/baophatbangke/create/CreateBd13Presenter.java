package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentContract;
import com.ems.dingdong.functions.mainhome.phathang.noptien.PaymentPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

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
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void postBD13AddNew(Bd13Create bd13Create) {
        mView.showProgress();
        mInteractor.bD13AddNew(bd13Create, new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSuccessMessage(response.body().getMessage());
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void searchLadingBd13(DingDongGetLadingCreateBD13Request objRequest) {
        mView.showProgress();
        mInteractor.searchLadingBd13(objRequest,new CommonCallback<SimpleResult>((Context) mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<DeliveryPostman> deliveryPostmens = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<DeliveryPostman>>(){}.getType());
                    mView.showListSuccess(deliveryPostmens);
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);

                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void callForward(String phone,String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
    public void updateMobile(String phone,String parcelCode) {
        mView.showProgress();
        mInteractor.updateMobile(parcelCode,"1", phone, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
}

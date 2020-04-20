package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;

import retrofit2.Call;
import retrofit2.Response;

public class CancelBD13Presenter extends Presenter<CancelBD13Contract.View, CancelBD13Contract.Interactor>
        implements CancelBD13Contract.Presenter {

    private CancelBD13TabContract.OnTabListener tabListener;

    CancelBD13Presenter(ContainerView containerView) {
        super(containerView);

    }

    @Override
    public void start() {

    }

    CancelBD13Presenter setOnTabListener(CancelBD13TabContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }

    @Override
    public CancelBD13Contract.Interactor onCreateInteractor() {
        return new CancelBD13Interactor(this);
    }

    @Override
    public CancelBD13Contract.View onCreateView() {
        return CancelBD13Fragment.getInstance();
    }


    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode) {
        mView.showProgress();
        mInteractor.getCancelDelivery(postmanCode, routeCode, fromDate, toDate, ladingCode, new CommonCallback<DingDongGetCancelDeliveryResponse>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<DingDongGetCancelDeliveryResponse> call, Response<DingDongGetCancelDeliveryResponse> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                assert response.body() != null;
                if (response.body().getErrorCode().equals("00")) {
                    mView.showListSuccess(response.body().getDeliveryPostmens());
                } else {
                    mView.showErrorToast(response.body().getMessage());
                }

            }

            @Override
            protected void onError(Call<DingDongGetCancelDeliveryResponse> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }

    @Override
    public void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList) {
        mView.showProgress();
        mInteractor.cancelDelivery(dingDongGetCancelDeliveryRequestList, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                assert response.body() != null;
                mView.showView(response.body().getMessage());
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
    public void onCanceled() {
        tabListener.onCanceledDelivery();
    }

    @Override
    public void titleChanged(int quantity, int currentSetTab) {
        tabListener.onQuantityChange(quantity, currentSetTab);
    }
}

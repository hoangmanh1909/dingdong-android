package com.ems.dingdong.functions.mainhome.location;

import android.app.Activity;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.CommonObjectResult;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Location Presenter
 */
public class LocationPresenter extends Presenter<LocationContract.View, LocationContract.Interactor>
        implements LocationContract.Presenter {

    public LocationPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public LocationContract.View onCreateView() {
        return LocationFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public LocationContract.Interactor onCreateInteractor() {
        return new LocationInteractor(this);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void findLocation(String ladingCode) {
        mView.showProgress();
        mInteractor.findLocation(ladingCode, new CommonCallback<CommonObjectResult>((Activity) mContainerView){
            @Override
            protected void onSuccess(Call<CommonObjectResult> call, Response<CommonObjectResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if(response.body().getErrorCode().equals("00"))
                {
                    mView.showFindLocationSuccess(response.body().getCommonObject());
                }
                else
                {
                    mView.showErrorToast(response.body().getMessage());
                    mView.showEmpty();
                }
            }

            @Override
            protected void onError(Call<CommonObjectResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }
        });
    }
}

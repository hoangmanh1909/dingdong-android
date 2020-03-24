package com.ems.dingdong.functions.mainhome.location;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void findLocation() {
        mView.fromView()
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(ladingCode -> mInteractor.findLocation(ladingCode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        commonObjectResult -> {
                            mView.hideProgress();
                            mView.showSuccessToast(commonObjectResult.getMessage());
                            if (commonObjectResult.getErrorCode().equals("00")) {
                                mView.showFindLocationSuccess(commonObjectResult.getCommonObject());
                            } else {
                                mView.showErrorToast(commonObjectResult.getMessage());
                                mView.showEmpty();
                            }
                        },
                        throwable -> {
                            mView.hideProgress();
                            mView.showErrorToast(throwable.getMessage());
                        }
                );
    }
}

package com.ems.dingdong.functions.mainhome.location;

import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The Location Presenter
 */
public class LocationPresenter extends Presenter<LocationContract.View, LocationContract.Interactor>
        implements LocationContract.Presenter {

    private String poCode;

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
        initPocode();
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
        if (TextUtils.isEmpty(poCode)) {
            initPocode();
        }
        mView.fromView()
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(ladingCode -> mInteractor.findLocation(ladingCode, poCode))
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

    private void initPocode() {
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String poCodeString = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(poCodeString)) {
            poCode = NetWorkController.getGson().fromJson(poCodeString, PostOffice.class).getCode();
        }
    }
}

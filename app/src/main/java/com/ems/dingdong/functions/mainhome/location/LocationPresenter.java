package com.ems.dingdong.functions.mainhome.location;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

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
    public void findLocation(String code) {
        if (TextUtils.isEmpty(poCode)) {
            initPocode();
        }
        mInteractor.findLocation(code, poCode)
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commonObjectResult -> {
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
                        }
                );
    }

    @Override
    public void callForward(String phone, String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        addCallback(mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showCallSuccess(response.body().getData());
                    } else {
                        mView.showCallError(response.body().getMessage());
                    }
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showCallError(message);
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable error) {
                super.onFailure(call, error);
                mView.showCallError("Lỗi kết nối đến tổng đài");
            }
        }));
    }

    private void initPocode() {
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String poCodeString = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(poCodeString)) {
            poCode = NetWorkController.getGson().fromJson(poCodeString, PostOffice.class).getCode();
        }
    }
}

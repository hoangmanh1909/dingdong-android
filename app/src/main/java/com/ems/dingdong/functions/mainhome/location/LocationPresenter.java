package com.ems.dingdong.functions.mainhome.location;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.NotiCtelPresenter;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
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
    private String code = "";

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

        if (!code.equals("")) {
            findLocation(code);

        }
    }

    @Override
    public LocationContract.Interactor onCreateInteractor() {
        return new LocationInteractor(this);
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    public LocationPresenter setCodeTicket(String codeTicket) {
        this.code = codeTicket;
        return this;
    }

    @Override
    public void ddCall(CallLiveMode r) {
        mView.showProgress();
        mInteractor.ddCall(r)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showCallLive(r.getToNumber());
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                    }
                    mView.hideProgress();
                });
    }

    @Override
    public void getHistoryCall(HistoryRequest request) {
        mView.showProgress();
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        request.setPOCode(poCode);
        request.setPostmanCode(userInfo.getUserName());
        request.setPostmanTel(userInfo.getMobileNumber());
        mInteractor.getHistoryCall(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null)
                        if (simpleResult.getErrorCode().equals("00")) {
                            HistoryRespone[] j = NetWorkController.getGson().fromJson(simpleResult.getData(), HistoryRespone[].class);
                            List<HistoryRespone> l = Arrays.asList(j);
                            mView.showLog(l);
                        } else {
                            mView.showError();
                        }
                    mView.hideProgress();
                }, throwable -> {
                    mView.hideProgress();
                });
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
                                HistoryRequest request = new HistoryRequest();
                                request.setLadingCode(code);
                                getHistoryCall(request);
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
    public String getCode() {
        return code;
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
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        addCallback(mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, userInfo.getiD(), poCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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

package com.ems.dingdong.functions.mainhome.phathang.sreachTracking;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class SreachCodePresenter extends Presenter<SreachCodeContract.View, SreachCodeContract.Interactor>
        implements SreachCodeContract.Presenter {

    public SreachCodePresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }
    private String code = "";
    @Override
    public SreachCodeContract.Interactor onCreateInteractor() {
        return new SreachCodeInteractor(this);
    }

    @Override
    public SreachCodeContract.View onCreateView() {
        return SreachCodeFragment.getInstance();
    }

    @Override
    public void findLocation(String code) {
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String poCodeString = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = "";
        if (!TextUtils.isEmpty(poCodeString)) {
            poCode = NetWorkController.getGson().fromJson(poCodeString, PostOffice.class).getCode();
        }
        mView.showProgress();
        mInteractor.findLocation(code.toUpperCase(), poCode)
                .delay(1500, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SimpleResult commonObjectResult) {
                        if (commonObjectResult.getErrorCode().equals("00")) {
                            CommonObject commonObject = NetWorkController.getGson().fromJson(commonObjectResult.getData(), CommonObject.class);
                            mView.showFindLocationSuccess(commonObject);
                            HistoryRequest request = new HistoryRequest();
                            request.setLadingCode(code);
                            mView.hideProgress();
                            getHistoryCall(request);
                        } else {
                            mView.showErrorToast(commonObjectResult.getMessage());
                            mView.showEmpty();
                            mView.hideProgress();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                    }
                });
    }


    @Override
    public void getHistoryCall(HistoryRequest request) {
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
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        try {
                            if (simpleResult.getErrorCode().equals("00")) {
                                HistoryRespone[] j = NetWorkController.getGson().fromJson(simpleResult.getData(), HistoryRespone[].class);
                                List<HistoryRespone> l = Arrays.asList(j);
                                mView.showLog(l);
                            } else {
//                                mView.showError();
                            }
                            mView.hideProgress();
                        } catch (Exception e) {
                            mView.hideProgress();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                    }


                });
    }

    public void ddCall(CallLiveMode r) {
        mView.showProgress();
        mInteractor.ddCall(r)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showCallLive(r.getToNumber());
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                        }
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.showToast(getViewContext(), e.getMessage());
                        mView.hideProgress();
                    }
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

    public SreachCodePresenter setCodeTicket(String codeTicket) {
        this.code = codeTicket;
        return this;
    }

    @Override
    public String getCode() {
        return code;
    }
    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }
}

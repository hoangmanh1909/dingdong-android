package com.ems.dingdong.functions.mainhome.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.AddressPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.GomHangPresenter;
import com.ems.dingdong.functions.mainhome.home.HomePresenter;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.MainMode;
import com.ems.dingdong.functions.mainhome.notify.ListNotifyPresenter;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangPresenter;
import com.ems.dingdong.functions.mainhome.profile.chat.ChatPresenter;
import com.ems.dingdong.functions.mainhome.profile.chat.menuchat.MenuChatPresenter;
import com.ems.dingdong.functions.mainhome.setting.SettingPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.BalanceRespone;
import com.ems.dingdong.model.MapMode;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.StatisticPaymentRequest;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.StatisticPaymentResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.ApiDisposable;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.api.ApiService;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapbox.mapboxsdk.Mapbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The Home Presenter
 */
public class MainPresenter extends Presenter<MainContract.View, MainContract.Interactor>
        implements MainContract.Presenter {

    public MainPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public MainContract.View onCreateView() {
        return MainFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

   public void getShift() {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
            mInteractor.getShift(postOffice.getCode(), new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    ArrayList<ShiftInfo> shiftInfos = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ShiftInfo>>() {
                    }.getType());
                    sharedPref.putString(Constants.KEY_POST_SHIFT, NetWorkController.getGson().toJson(shiftInfos));
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    mView.hideProgress();
                    super.onError(call, message);
                    mView.showErrorToast(message);
                }

                @Override
                public void onFailure(Call<SimpleResult> call, Throwable error) {
                    super.onFailure(call, error);
                    mView.hideProgress();
                    new ApiDisposable(error, getViewContext());
                }
            });
        }
    }

    @Override
    public void showChat() {
        new MenuChatPresenter(mContainerView).addView();
    }

    @SuppressLint("CheckResult")
    @Override
    public void getListTicket(TicketNotifyRequest request) {
        try {
            if (request != null)
                mInteractor.getListTicket(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(simpleResult -> {
                            if (simpleResult.getErrorCode() != null)
                                if (simpleResult.getErrorCode().equals("00")) {
                                    @SuppressLint("CheckResult") TicketNotifyRespone[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), TicketNotifyRespone[].class);
                                    List<TicketNotifyRespone> list1 = Arrays.asList(list);
                                    mView.hideProgress();
                                    mView.showListNotifi(list1);
                                } else {
                                    mView.hideProgress();
                                    mView.showListNotifi(new ArrayList<>());
                                }
                        }, throwable -> {
                            mView.hideProgress();
                            new ApiDisposable(throwable, getViewContext());
                        });
        } catch (Exception e) {
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void getVaoCa(MainMode request) {
        try {
            mView.showProgress();
            mInteractor.getVaoCa(request)
                    .subscribeOn(Schedulers.io())
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
//                        if (simpleResult.getErrorCode().equals("00")) {
//                            mView.showVaoCa(simpleResult.getData());
//                            mView.hideProgress();
//                        } else {
//                            mView.showError();
//                            Toast.showToast(getViewContext(), simpleResult.getMessage());
//                            mView.hideProgress();
//                        }
                    }, throwable -> {
                        mView.hideProgress();
                        new ApiDisposable(throwable, getViewContext());
                    });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void getRaCa(String request) {
        try {
            mView.showProgress();
//            mInteractor.getRaCa(request)
//                    .subscribeOn(Schedulers.io())
//                    .delay(1000, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(simpleResult -> {
////                        if (simpleResult.getErrorCode().equals("00")) {
////                            mView.showRaCa(simpleResult.getData());
////                            mView.hideProgress();
////                        } else {
////                            mView.showError();
////                            Toast.showToast(getViewContext(), simpleResult.getMessage());
////                            mView.hideProgress();
////                        }
//                    }, throwable -> {
//                        mView.hideProgress();
//                        new ApiDisposable(throwable, getViewContext());
//                    });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void getCallLog(List<CallLogMode> request) {
        mView.showProgress();
        mInteractor.getCallLog(request)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showCallLog(request.size());
                        mView.hideProgress();
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                }, throwable -> {
                    mView.hideProgress();
                    new ApiDisposable(throwable, getViewContext());
                });
    }

//    @SuppressLint("CheckResult")
//    @Override
//    public void getBalance() {
//        SharedPref sharedPref = new SharedPref((Context) mContainerView);
//        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
//        UserInfo userInfo = null;
//        PostOffice postOffice = null;
//        String fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//        String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
//        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
//        if (!posOfficeJson.isEmpty()) {
//            postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
//        }
//        if (!userJson.isEmpty()) {
//            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
//        }
//        mInteractor.getBalance(userInfo.getiD(), postOffice.getCode(), userInfo.getMobileNumber(), fromDate, toDate)
//                .subscribeOn(Schedulers.io())
//                .delay(1000, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(simpleResult -> {
//                    try {
//                        if (simpleResult.getErrorCode().equals("00")) {
////                            StatisticPaymentResponse paymentResponse = NetWorkController.getGson().fromJson(simpleResult.getValue(), new TypeToken<StatisticPaymentResponse>() {
////                            }.getType());
//                            mView.updateBalance(simpleResult.getStatisticPaymentResponses());
//                        } else {
//                            Toast.showToast(getViewContext(), simpleResult.getMessage());
//                            mView.hideProgress();
//                        }
//                    } catch (Exception e) {
//
//                    }
//
//                }, throwable -> {
//                    mView.hideProgress();
//                    new ApiDisposable(throwable, getViewContext());
//                });
//    }
//
//    @Override
//    public void ddGetBalance(BalanceModel v) {
//        mView.showProgress();
//        mInteractor.ddGetBalance(v, new CommonCallback<SimpleResult>((Activity) mContainerView) {
//            @Override
//            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
//                super.onSuccess(call, response);
//                try {
//                    Log.d("ddGetBalance", new Gson().toJson(response.body()));
//                    if (response.body().getErrorCode().equals("00")) {
//                        mView.setBalance(response.body().getData());
//                        mView.hideProgress();
//                    } else {
//                        mView.setBalance(null);
//                        mView.hideProgress();
//                    }
//                } catch (Exception e) {
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<SimpleResult> call, Throwable error) {
//                super.onFailure(call, error);
//                mView.hideProgress();
//                new ApiDisposable(error, getViewContext());
//            }
//        });
//
//    }


    @Override
    public void ddGetPaymentStatistic() {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        PostOffice postOffice = null;
        String fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            postOffice = ApiService.getGson().fromJson(posOfficeJson, PostOffice.class);
        }
        if (!userJson.isEmpty()) {
            userInfo = ApiService.getGson().fromJson(userJson, UserInfo.class);
        }
        StatisticPaymentRequest statisticPaymentRequest = new StatisticPaymentRequest(
                userInfo.getiD(), postOffice.getCode(), userInfo.getMobileNumber(), toDate, fromDate
        );
        mInteractor.ddGetPayment(statisticPaymentRequest)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode() != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                StatisticPaymentResponse paymentResponse = ApiService.getGson().fromJson(simpleResult.getData(), new TypeToken<StatisticPaymentResponse>() {
                                }.getType());
                                mView.showPayment(paymentResponse);
                                mView.hideProgress();
                            } else if (simpleResult.getErrorCode().equals("01")) {
                                mView.showLoiPayment();
                                mView.hideProgress();
                            } else {
                                mView.showLoiHeThong();
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showErrorToast(e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getTienHome() {
        String fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String routeInfoJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        UserInfo userInfo = null;
        PostOffice postOffice = null;
        RouteInfo routeInfo = null;
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            postOffice = ApiService.getGson().fromJson(posOfficeJson, PostOffice.class);
        }
        if (!userJson.isEmpty()) {
            userInfo = ApiService.getGson().fromJson(userJson, UserInfo.class);
        }
        if (!routeInfoJson.isEmpty()) {
            routeInfo = ApiService.getGson().fromJson(routeInfoJson, RouteInfo.class);
        }
        BalanceModel v = new BalanceModel();
        v.setToDate(Integer.parseInt(toDate));
        v.setFromDate(Integer.parseInt(fromDate));
        v.setPOProvinceCode(userInfo.getPOProvinceCode());
        v.setPODistrictCode(userInfo.getPODistrictCode());
        v.setPOCode(postOffice.getCode());
        v.setPostmanCode(userInfo.getUserName());
        v.setPostmanId(userInfo.getiD());
        v.setRouteCode(routeInfo.getRouteCode());
        v.setRouteId(Long.parseLong(routeInfo.getRouteId()));


        mInteractor.ddGetTienHome(v)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull SimpleResult simpleResult) {
                        if (simpleResult.getErrorCode() != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                BalanceRespone balance = ApiService.getGson().fromJson(simpleResult.getData(), BalanceRespone.class);
                                mView.showTienHome(balance);
                                mView.hideProgress();
                            } else if (simpleResult.getErrorCode().equals("01")) {
                                mView.showLoiTienHome();
                                mView.hideProgress();
                            } else {
                                mView.showLoiHeThong();
                                mView.hideProgress();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showErrorToast(e.getMessage());
                        mView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showNitify() {
        new ListNotifyPresenter(mContainerView).pushView();
    }

    @Override
    public MainContract.Interactor onCreateInteractor() {
        return new MainInteractor(this);
    }

    @Override
    public HomePresenter getHomePresenter() {
        return new HomePresenter(mContainerView);
    }

    @Override
    public GomHangPresenter getGomHangPresenter() {
        return new GomHangPresenter(mContainerView);
    }

    @Override
    public PhatHangPresenter getPhatHangPresenter() {
        return new PhatHangPresenter(mContainerView);
    }

    @Override
    public LocationPresenter getLocationPresenter() {
        return new LocationPresenter(mContainerView);
    }

    @Override
    public AddressPresenter getAddressPresenter() {
        return new AddressPresenter(mContainerView);
    }

    @Override
    public void showSetting() {
        new SettingPresenter(mContainerView).pushView();
    }

    @Override
    public void getMap() {
        mInteractor.getMap()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        MapMode[] searchMode = NetWorkController.getGson().fromJson(simpleResult.getData(), MapMode[].class);
                        List<MapMode> list1 = Arrays.asList(searchMode);
                        SharedPref sharedPref = new SharedPref((Context) mContainerView);
                        sharedPref.putString(Constants.KEY_GG_MAP, NetWorkController.getGson().toJson(list1.get(0)));
                        mView.hideProgress();
                    } else {
                        mView.hideProgress();
                    }
                }, throwable -> {
                    mView.hideProgress();
                    new ApiDisposable(throwable, getViewContext());
                });


    }
}

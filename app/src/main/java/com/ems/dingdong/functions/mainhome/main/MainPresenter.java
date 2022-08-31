package com.ems.dingdong.functions.mainhome.main;

import android.app.Activity;
import android.content.Context;

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
import com.ems.dingdong.functions.mainhome.setting.SettingPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.MapMode;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ShiftInfo;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.StatisticPaymentResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
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
        getShift();
    }

    private void getShift() {
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
            });
        }
    }

    @Override
    public void getListTicket(TicketNotifyRequest request) {
        mInteractor.getListTicket(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        TicketNotifyRespone[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), TicketNotifyRespone[].class);
                        List<TicketNotifyRespone> list1 = Arrays.asList(list);
                        mView.hideProgress();
                        mView.showListNotifi(list1);
                    } else {
                        mView.hideProgress();
                        mView.showListNotifi(new ArrayList<>());
                    }
                });
    }

    @Override
    public void getVaoCa(MainMode request) {
        try {
            mView.showProgress();
            mInteractor.getVaoCa(request)
                    .subscribeOn(Schedulers.io())
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showVaoCa(simpleResult.getData());
                            mView.hideProgress();
                        } else {
                            mView.showError();
                            mView.hideProgress();
                        }
                    }, Throwable::getMessage);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void getRaCa(String request) {
        try {
            mView.showProgress();
            mInteractor.getRaCa(request)
                    .subscribeOn(Schedulers.io())
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.showRaCa(simpleResult.getData());
                            mView.hideProgress();
                        } else {
                            mView.showError();
                            mView.hideProgress();
                        }
                    }, Throwable::getMessage);
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
                        mView.showCallLog(simpleResult.getData());
                        mView.hideProgress();
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void getBalance() {

        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        PostOffice postOffice = null;
        String fromDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String toDate = DateTimeUtils.convertDateToString(Calendar.getInstance().getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String posOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!posOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(posOfficeJson, PostOffice.class);
        }
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }
        mInteractor.getBalance(userInfo.getiD(), postOffice.getCode(), userInfo.getMobileNumber(), fromDate, toDate)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    try{
                        if (simpleResult.getErrorCode().equals("00")) {
//                            StatisticPaymentResponse paymentResponse = NetWorkController.getGson().fromJson(simpleResult.getValue(), new TypeToken<StatisticPaymentResponse>() {
//                            }.getType());
                            mView.updateBalance(simpleResult.getStatisticPaymentResponses());
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.hideProgress();
                        }
                    }catch (Exception e){
                        Log.d("ASDASDASDw1e123", "ASDAsdq2");

                    }

                });
//        mInteractor.getBalance(userInfo.getiD(), postOffice.getCode(), userInfo.getMobileNumber(), fromDate, toDate,
//                new CommonCallback<StatisticPaymentResult>((Activity) mContainerView) {
//            @Override
//            protected void onSuccess(Call<StatisticPaymentResult> call, Response<StatisticPaymentResult> response) {
//                if (response.body().getErrorCode().equals("00")){
//                    Log.d("ASDASDASDw1e123","ASDAsdq2");
//                    StatisticPaymentResponse paymentResponse = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<StatisticPaymentResponse>(){}.getType());
//                    mView.updateBalance(paymentResponse);
//                }
//                mView.hideProgress();
//            }
//
//            @Override
//            protected void onError(Call<StatisticPaymentResult> call, String message) {
//            }
//        });
    }

    @Override
    public void ddGetBalance(BalanceModel v) {
        mView.showProgress();
        mInteractor.ddGetBalance(v, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                try {
                    Log.d("ddGetBalance", new Gson().toJson(response.body()));
                    if (response.body().getErrorCode().equals("00")) {
                        mView.setBalance(response.body().getData());
                        mView.hideProgress();
                    } else {
                        mView.setBalance(null);
                        mView.hideProgress();
                    }
                } catch (Exception e) {
                }


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
                }, Throwable::printStackTrace);


    }
}

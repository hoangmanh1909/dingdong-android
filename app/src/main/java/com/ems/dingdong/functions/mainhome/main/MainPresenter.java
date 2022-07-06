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
import com.ems.dingdong.functions.mainhome.notify.ListNotifyPresenter;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangPresenter;
import com.ems.dingdong.functions.mainhome.setting.SettingPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.MapMode;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;
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
            mInteractor.getShift(postOffice.getCode(), new CommonCallback<ShiftResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<ShiftResult> call, Response<ShiftResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                    sharedPref.putString(Constants.KEY_POST_SHIFT, NetWorkController.getGson().toJson(response.body().getShiftInfos()));
                }

                @Override
                protected void onError(Call<ShiftResult> call, String message) {
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

        mInteractor.getBalance(userInfo.getiD(), postOffice.getCode(), userInfo.getMobileNumber(), fromDate, toDate,
                new CommonCallback<StatisticPaymentResult>((Activity) mContainerView) {
                    @Override
                    protected void onSuccess(Call<StatisticPaymentResult> call, Response<StatisticPaymentResult> response) {
                        mView.hideProgress();
                        if (getViewContext() != null)
                            mView.updateBalance(response.body().getStatisticPaymentResponses());
                    }

                    @Override
                    protected void onError(Call<StatisticPaymentResult> call, String message) {
                    }
                });
    }

    @Override
    public void ddGetBalance(BalanceModel v) {
        mView.showProgress();
        mInteractor.ddGetBalance(v, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                try {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.setBalance(response.body().getData());
                        mView.hideProgress();
                    } else {

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
                }, throwable -> {
                    mView.hideProgress();
                });


    }
}

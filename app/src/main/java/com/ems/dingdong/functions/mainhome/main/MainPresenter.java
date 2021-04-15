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
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;

import java.util.Calendar;

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

        mInteractor.getBalance(userInfo.getiD(), postOffice.getCode(), userInfo.getMobileNumber(), fromDate, toDate, new CommonCallback<StatisticPaymentResult>((Activity) mContainerView) {
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
}

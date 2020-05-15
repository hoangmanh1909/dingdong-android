package com.ems.dingdong.notification;

import android.app.Activity;
import android.content.Intent;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.callservice.history.HistoryCallPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.listcommon.ListCommonActivity;
import com.ems.dingdong.model.NotificationResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The Notification Presenter
 */
public class NotificationPresenter extends Presenter<NotificationContract.View, NotificationContract.Interactor>
        implements NotificationContract.Presenter {

    private boolean isNotificationEmpty = false;

    public NotificationPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public NotificationContract.View onCreateView() {
        return NotificationFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
    }

    @Override
    public NotificationContract.Interactor onCreateInteractor() {
        return new NotificationInteractor(this);
    }

    @Override
    public void getNotifications(String mobileNumber) {
        mView.showProgress();
        mInteractor.getNotifications(mobileNumber, new CommonCallback<NotificationResult>((Activity) mContainerView) {
            @Override
            protected void onError(Call<NotificationResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showErrorToast(message);
            }

            @Override
            public void onResponse(Call<NotificationResult> call, Response<NotificationResult> response) {
                super.onResponse(call, response);
                mView.hideProgress();
                if (response.body().getErrorCode().equals("00")) {
                    mView.showListNotifications(response.body().getNotificationInfoList());
                }
            }
        });
    }

    @Override
    public void updateRead(String mobileNumber) {
        if (!isNotificationEmpty) {
            mInteractor.updateRead(mobileNumber, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                }

                @Override
                public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onResponse(call, response);
                    if (response.body().getErrorCode().equals("00")) {
                        SharedPref sharedPref = new SharedPref(getViewContext());
                        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
                        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
                        userInfo.setCountNotification("0");
                        sharedPref.putString(Constants.KEY_USER_INFO, NetWorkController.getGson().toJson(userInfo));
                    }
                }
            });
        }
    }

    @Override
    public void showCallHistory() {
        new HistoryCallPresenter(mContainerView).presentView();
    }

    @Override
    public void showCollectFragment() {
        Intent intent = new Intent(getViewContext(), ListCommonActivity.class);
        intent.putExtra(Constants.TYPE_GOM_HANG, 1);
        getViewContext().startActivity(intent);
    }

    public NotificationPresenter setHaveNewNotify(boolean isNotificationEmpty) {
        this.isNotificationEmpty = isNotificationEmpty;
        return this;
    }
}

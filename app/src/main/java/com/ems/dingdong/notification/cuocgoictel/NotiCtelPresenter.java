package com.ems.dingdong.notification.cuocgoictel;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.NotiCtelModel;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * The Notification Presenter
 */
public class NotiCtelPresenter extends Presenter<NotiCtelContract.View, NotiCtelContract.Interactor>
        implements NotiCtelContract.Presenter {

    String codeTicket;

    public NotiCtelPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public NotiCtelContract.View onCreateView() {
        return NotiCtelFragment.getInstance();
    }

    @Override
    public void start() {
        // Start getting data here
        getDetail(codeTicket);
    }

    @Override
    public NotiCtelContract.Interactor onCreateInteractor() {
        return new NotiCtelInteractor(this);
    }

    @Override
    public String setCodeTicket() {
        return codeTicket;
    }


    @Override
    public void getDetail(String ticket) {
        mView.showProgress();
        mInteractor.getListTicket(ticket)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mView.showInfo(NetWorkController.getGson().fromJson(simpleResult.getData(), NotiCtelModel.class));
                        mView.hideProgress();
                    } else {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
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
//        request.setFromDate(20210617);
//        request.setToDate(20220627);
        mInteractor.getHistoryCall(request)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MICROSECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {

                    } else {

                    }
                    mView.hideProgress();
                });
    }

    public NotiCtelPresenter setCodeTicket(String codeTicket) {
        this.codeTicket = codeTicket;
        return this;
    }

    @Override
    public void callForward(String phone, String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, userInfo.getiD(), poCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
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
        });

    }

    @Override
    public void showTraCuu(String parcelCode) {
        new LocationPresenter(mContainerView).setCodeTicket(parcelCode).pushView();
    }
}

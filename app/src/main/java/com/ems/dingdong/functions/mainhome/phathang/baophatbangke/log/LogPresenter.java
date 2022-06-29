package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.NotiCtelPresenter;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LogPresenter extends Presenter<LogContract.View, LogContract.Interactor>
        implements LogContract.Presenter {

    public LogPresenter(ContainerView containerView) {
        super(containerView);
    }

    String code;

    @Override
    public void start() {
        HistoryRequest request = new HistoryRequest();
        request.setLadingCode("EC521602781VN");
        getHistoryCall(request);
    }

    @Override
    public LogContract.Interactor onCreateInteractor() {
        return new LogInteractor(this);
    }

    @Override
    public LogContract.View onCreateView() {
        return LogFragment.getInstance();
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
                    if (simpleResult.getErrorCode().equals("00")) {

                        HistoryRespone[] j
                                = NetWorkController.getGson().fromJson(simpleResult.getData(), HistoryRespone[].class);
                        List<HistoryRespone> l = Arrays.asList(j);
                        mView.showLog(l);

                    } else {

                        mView.showError();
                    }
                    mView.hideProgress();
                });
    }

    public LogPresenter setCode(String code) {
        this.code = code;
        return this;
    }

}

package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi;

import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.TabLogCallRespone;
import com.ems.dingdong.functions.mainhome.phathang.receverpersion.ReceverPersonPresenter;
import com.ems.dingdong.functions.mainhome.phathang.sreachTracking.SreachCodePresenter;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LogCallPresenter extends Presenter<LogCallContract.View, LogCallContract.Interactor>
        implements LogCallContract.Presenter {

    public LogCallPresenter(ContainerView containerView) {
        super(containerView);
    }

    int type;
    TabLogCallRespone tabLogCallRespone;

    @Override
    public void start() {
        HistoryRequest historyRequest = new HistoryRequest();
        historyRequest.setFromDate(Integer.parseInt(tabLogCallRespone.getFromDate()));
        historyRequest.setToDate(Integer.parseInt(tabLogCallRespone.getToDate()));
        historyRequest.setStatus(type);
        getHistoryCall(historyRequest);
    }

    @Override
    public LogCallContract.Interactor onCreateInteractor() {
        return new LogCallInteractor(this);
    }

    @Override
    public LogCallContract.View onCreateView() {
        return LogCallFragment.getInstance();
    }

    @Override
    public void getHistoryCall(HistoryRequest request) {
        try {
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
                            HistoryRespone[] j = NetWorkController.getGson().fromJson(simpleResult.getData(), HistoryRespone[].class);
                            List<HistoryRespone> l = Arrays.asList(j);
                            mView.showLog(l);

                        } else {
                        }
                        mView.hideProgress();
                    });
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public TabLogCallRespone getLogCallMode() {
        return tabLogCallRespone;
    }

    @Override
    public int mType() {
        return type;
    }

    @Override
    public void showTrCuubg(String bg) {
        new SreachCodePresenter(mContainerView).setCodeTicket(bg).pushView();
    }


    public LogCallPresenter setType(int type) {
        this.type = type;
        return this;
    }


    public LogCallPresenter setLogCall(TabLogCallRespone tabLogCallRespone) {
        this.tabLogCallRespone = tabLogCallRespone;
        return this;
    }
}

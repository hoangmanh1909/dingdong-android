package com.ems.dingdong.functions.mainhome.main;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.TokenMoveCropResult;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;

/**
 * The Home interactor
 */
class MainInteractor extends Interactor<MainContract.Presenter>
        implements MainContract.Interactor {

    MainInteractor(MainContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getShift(String code, CommonCallback<SimpleResult> callback) {
        NetWorkController.getPostmanShift(code, callback);
    }

    @Override
    public void getBalance(String postmanID, String poCode, String phoneNumber, String fromDate, String toDate, CommonCallback<SimpleResult> callback) {
        NetWorkController.statisticPayment(postmanID, poCode, phoneNumber, fromDate, toDate, callback);
    }

    @Override
    public Single<TokenMoveCropResult> getAccessToken(String mobileNumber) {
        return NetWorkController.getAccessTokenAndroid(mobileNumber);
    }
}

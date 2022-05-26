package com.ems.dingdong.functions.mainhome.main;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.TokenMoveCropResult;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.network.NetWorkController;

import io.reactivex.Single;
import retrofit2.Call;

/**
 * The Home interactor
 */
class MainInteractor extends Interactor<MainContract.Presenter>
        implements MainContract.Interactor {

    MainInteractor(MainContract.Presenter presenter) {
        super(presenter);
    }

    @Override
    public void getShift(String code, CommonCallback<ShiftResult> callback) {
        NetWorkController.getPostmanShift(code, callback);
    }

    @Override
    public void getBalance(String postmanID, String poCode, String phoneNumber, String fromDate, String toDate, CommonCallback<StatisticPaymentResult> callback) {
        NetWorkController.statisticPayment(postmanID, poCode, phoneNumber, fromDate, toDate, callback);
    }

    @Override
    public Single<TokenMoveCropResult> getAccessToken(String mobileNumber) {
        return NetWorkController.getAccessTokenAndroid(mobileNumber);
    }

    @Override
    public void ddGetBalance(BalanceModel requset, CommonCallback<SimpleResult> callback) {
        NetWorkController.ddGetBalance(requset, callback);
    }

    @Override
    public Single<SimpleResult> getListTicket(TicketNotifyRequest request) {
        return NetWorkController.getListTicket(request);
    }
//    @Override
//    public void ddGetBalance(BalanceModel request) {
//        return NetWorkController.ddGetBalance(request);
//    }
}

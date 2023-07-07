package com.ems.dingdong.functions.mainhome.main;

import com.core.base.viper.Interactor;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.MainMode;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.TokenMoveCropResult;
import com.ems.dingdong.model.request.StatisticPaymentRequest;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.network.NetWorkControllerGateWay;
import com.ems.dingdong.network.api.ApiService;

import java.util.List;

import io.reactivex.Observable;
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
    public void getShift(String code, CommonCallback<SimpleResult> callback) {
        NetWorkControllerGateWay.getPostmanShift(code, callback);
    }

//    @Override
//    public Single<StatisticPaymentResult> getBalance(String postmanID, String poCode, String phoneNumber, String fromDate, String toDate) {
//        return NetWorkControllerGateWay.statisticPayment(postmanID, poCode, phoneNumber, fromDate, toDate);
//    }

//    @Override
//    public void getBalance(String postmanID, String poCode, String phoneNumber, String fromDate, String toDate, CommonCallback<StatisticPaymentResult> callback) {
//        NetWorkControllerGateWay.statisticPayment(postmanID, poCode, phoneNumber, fromDate, toDate, callback);
//    }

//    @Override
//    public Single<TokenMoveCropResult> getAccessToken(String mobileNumber) {
//        return NetWorkController.getAccessTokenAndroid(mobileNumber);
//    }

    @Override
    public Single<SimpleResult> getMap() {
        return NetWorkControllerGateWay.getMap();
    }

//    @Override
//    public void ddGetBalance(BalanceModel requset, CommonCallback<SimpleResult> callback) {
//        NetWorkControllerGateWay.ddGetBalance(requset, callback);
//    }

    @Override
    public Single<SimpleResult> getListTicket(TicketNotifyRequest request) {
        return NetWorkControllerGateWay.getListTicket(request);
    }

    @Override
    public Single<SimpleResult> getVaoCa(MainMode request) {
        return NetWorkControllerGateWay.getVaoCa(request);
    }

    @Override
    public Single<SimpleResult> getRaCa(MainMode request) {
        return NetWorkControllerGateWay.getRaCa(request);
    }

    @Override
    public Single<SimpleResult> getCallLog(List<CallLogMode> request) {
        return NetWorkControllerGateWay.getCallLog(request);
    }

    @Override
    public Observable<SimpleResult> ddGetPayment(StatisticPaymentRequest statisticPaymentRequest) {
        return ApiService.ddGetPaymentStatistic(statisticPaymentRequest);
    }

    @Override
    public Observable<SimpleResult> ddGetTienHome(BalanceModel balanceModel) {
        return ApiService.ddGetTienHome(balanceModel);
    }
//    @Override
//    public void ddGetBalance(BalanceModel request) {
//        return NetWorkController.ddGetBalance(request);
//    }
}

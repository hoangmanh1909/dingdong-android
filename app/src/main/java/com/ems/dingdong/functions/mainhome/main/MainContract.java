package com.ems.dingdong.functions.mainhome.main;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.AddressPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.GomHangPresenter;
import com.ems.dingdong.functions.mainhome.home.HomePresenter;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.MainMode;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.BalanceRespone;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.TokenMoveCropResult;
import com.ems.dingdong.model.request.StatisticPaymentRequest;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.StatisticPaymentResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

/**
 * The Home Contract
 */
interface MainContract {

    interface Interactor extends IInteractor<Presenter> {
        void getShift(String code, CommonCallback<SimpleResult> callback);

//        Single<StatisticPaymentResult> getBalance(String postmanID, String poCode, String phoneNumber, String fromDate, String toDate);

        Single<SimpleResult> getMap();

//        void ddGetBalance(BalanceModel requset, CommonCallback<SimpleResult> callback);

        Single<SimpleResult> getListTicket(TicketNotifyRequest request);

        Single<SimpleResult> getVaoCa(MainMode request);

        Single<SimpleResult> getRaCa(String request);

        Single<SimpleResult> getCallLog(List<CallLogMode> request);

        Observable<SimpleResult> ddGetPayment(StatisticPaymentRequest statisticPaymentRequest);

        Observable<SimpleResult> ddGetTienHome(BalanceModel balanceModel);
    }

    interface View extends PresentView<Presenter> {
//        void updateBalance(StatisticPaymentResponse value);
//
//        void setBalance(String x);
//
        void showListNotifi(List<TicketNotifyRespone> list);
//
//        void showVaoCa(String data);
//
//        void showRaCa(String data);
//
        void showCallLog(int size);
//
//        void showError();
//
//        void showErrorRaCa();

        void showPayment(StatisticPaymentResponse value);

        void showLoiPayment();

        void showTienHome(BalanceRespone value);

        void showLoiTienHome();

        void showLoiHeThong();
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showChat();

        void getListTicket(TicketNotifyRequest request);

        void getVaoCa(MainMode request);

        void getRaCa(String request);

        void getCallLog(List<CallLogMode> request);

        HomePresenter getHomePresenter();

        GomHangPresenter getGomHangPresenter();

        PhatHangPresenter getPhatHangPresenter();

        LocationPresenter getLocationPresenter();

        AddressPresenter getAddressPresenter();

        void showSetting();

        void getMap();

//        void getBalance();
//
//        void ddGetBalance(BalanceModel v);

        void showNitify();

        void getShift();

        void ddGetPaymentStatistic();

        void getTienHome();
    }
}




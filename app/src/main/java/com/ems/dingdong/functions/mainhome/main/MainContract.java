package com.ems.dingdong.functions.mainhome.main;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.address.AddressPresenter;
import com.ems.dingdong.functions.mainhome.gomhang.GomHangPresenter;
import com.ems.dingdong.functions.mainhome.home.HomePresenter;
import com.ems.dingdong.functions.mainhome.location.LocationPresenter;
import com.ems.dingdong.functions.mainhome.phathang.PhatHangPresenter;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.TokenMoveCropResult;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.response.StatisticPaymentResponse;
import com.ems.dingdong.model.response.TicketNotifyRespone;
import com.ems.dingdong.network.NetWorkController;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

/**
 * The Home Contract
 */
interface MainContract {

    interface Interactor extends IInteractor<Presenter> {
        void getShift(String code, CommonCallback<ShiftResult> callback);

        void getBalance(String postmanID, String poCode, String phoneNumber, String fromDate, String toDate, CommonCallback<StatisticPaymentResult> callback);

        Single<TokenMoveCropResult> getAccessToken(String mobileNumber);

        Single<SimpleResult> getMap();

        void ddGetBalance(BalanceModel requset, CommonCallback<SimpleResult> callback);

        Single<SimpleResult> getListTicket(TicketNotifyRequest request);

    }

    interface View extends PresentView<Presenter> {
        void updateBalance(StatisticPaymentResponse value);

        void setBalance(String x);

        void showListNotifi(List<TicketNotifyRespone> list);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getListTicket(TicketNotifyRequest request);

        HomePresenter getHomePresenter();

        GomHangPresenter getGomHangPresenter();

        PhatHangPresenter getPhatHangPresenter();

        LocationPresenter getLocationPresenter();

        AddressPresenter getAddressPresenter();

        void showSetting();

        void getMap();

        void getBalance();

        void ddGetBalance(BalanceModel v);

        void showNitify();
    }
}




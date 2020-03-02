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
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.response.StatisticPaymentResponse;

/**
 * The Home Contract
 */
interface MainContract {

    interface Interactor extends IInteractor<Presenter> {
        void getShift(String code, CommonCallback<ShiftResult> callback);

        void getBalance(String postmanID, String poCode, String phoneNumber, String fromDate, String toDate, CommonCallback<StatisticPaymentResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void updateBalance(StatisticPaymentResponse value);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        HomePresenter getHomePresenter();

        GomHangPresenter getGomHangPresenter();

        PhatHangPresenter getPhatHangPresenter();

        LocationPresenter getLocationPresenter();

        AddressPresenter getAddressPresenter();

        void showSetting();

        void getBalance();
    }
}




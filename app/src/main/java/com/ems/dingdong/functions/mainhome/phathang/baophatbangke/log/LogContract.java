package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.log;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;

import java.util.List;

import io.reactivex.Single;

public interface LogContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getHistoryCall(HistoryRequest historyRequest);
    }

    interface View extends PresentView<Presenter> {
        void showLog(List<HistoryRespone> l);

        void showError();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void getHistoryCall(HistoryRequest request);


    }
}

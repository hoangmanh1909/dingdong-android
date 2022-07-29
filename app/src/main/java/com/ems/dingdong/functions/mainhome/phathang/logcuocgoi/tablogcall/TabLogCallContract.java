package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.TabLogCallRespone;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRespone;

import java.util.List;

import io.reactivex.Single;

public interface TabLogCallContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getHistoryCallToal(HistoryRequest historyRequest);
    }

    interface View extends PresentView<Presenter> {
        void showLog(List<TabLogCallRespone> l);

        void showError(String mess);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void showLogCall(int type, TabLogCallRespone tabLogCallRespone);

        void getHistoryCall(HistoryRequest request);
    }
}

package com.ems.dingdong.functions.mainhome.profile.prepaid;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.PrepaidResult;
import com.ems.dingdong.model.PrepaidValueResponse;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;


public interface PrepaidContract {

    interface Interactor extends IInteractor<Presenter> {
        void getPrepaidInfo(String phoneNumber, CommonCallback<PrepaidResult> callback);

        void getHistory(String phoneNumber, CommonCallback<SimpleResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showRegisterView();

        void showInfo(PrepaidValueResponse value);

        void showHistorySucces(List<SimpleResult> result);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getPrepaidInfo();

        void showRegisterView();

        void getHistory();
    }
}

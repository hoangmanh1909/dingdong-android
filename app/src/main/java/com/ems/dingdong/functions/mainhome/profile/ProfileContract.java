package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;

import io.reactivex.Single;

/**
 * The Profile Contract
 */
interface ProfileContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getCallLog(List<CallLogMode> request);
    }

    interface View extends PresentView<Presenter> {

        void showCallLog(int size);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void moveToEWallet();

        void showLichsuCuocgoi();

        void showLuong();

        void getCallLog(List<CallLogMode> request);

        void showChat();

        void showTraceLog();
    }
}




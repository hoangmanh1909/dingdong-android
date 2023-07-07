package com.ems.dingdong.functions.mainhome.profile;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.main.data.MainMode;
import com.ems.dingdong.model.SimpleResult;

import java.util.List;

import io.reactivex.Single;

/**
 * The Profile Contract
 */
interface ProfileContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getCallLog(List<CallLogMode> request);

          Single<SimpleResult> getVaoCa(MainMode request);

        Single<SimpleResult> getRaCa(MainMode request);

    }

    interface View extends PresentView<Presenter> {

        void showCallLog(int size);

        void showVaoCa(String data);

        void showRaCa(String data);

        void showError(int tyoe);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void moveToEWallet();

        void showLichsuCuocgoi();

        void showLuong();

        void getCallLog(List<CallLogMode> request);

        void showChat();

        void showTraceLog();

        void getVaoCa(MainMode request);

        void getRaCa(MainMode request);
    }
}




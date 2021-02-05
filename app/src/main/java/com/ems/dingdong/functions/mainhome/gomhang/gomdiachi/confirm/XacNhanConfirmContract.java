package com.ems.dingdong.functions.mainhome.gomhang.gomdiachi.confirm;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostman;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import java.util.ArrayList;

/**
 * The Setting Contract
 */
interface XacNhanConfirmContract {

    interface Interactor extends IInteractor<Presenter> {
        void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback);
    }

    interface View extends PresentView<Presenter> {
        void showError(String message);

        void showResult(ConfirmAllOrderPostman allOrderPostman);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        ArrayList<ConfirmOrderPostman> getList();

        void confirmAllOrderPostman();
    }
}




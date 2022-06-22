package com.ems.dingdong.functions.mainhome.profile.ewallet.lienket;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.SimpleResult;

import org.apache.poi.ss.formula.functions.T;

import io.reactivex.Single;

public interface LinkBankContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> getDDsmartBankConfirmLinkRequest(BaseRequestModel request);
    }

    interface View extends PresentView<Presenter> {


    }

    interface Presenter extends IPresenter<View, Interactor> {

        void getDDsmartBankConfirmLinkRequest(BaseRequestModel x);

    }
}

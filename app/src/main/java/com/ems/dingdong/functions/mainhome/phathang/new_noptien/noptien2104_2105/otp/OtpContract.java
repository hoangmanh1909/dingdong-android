package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.otp;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.response.SmartBankLink;

import java.util.List;

import io.reactivex.Single;

public interface OtpContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel);
    }

    interface View extends PresentView<Presenter> {

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void confirmPayment( String otp);

        SmartBankLink getSmartBankLink();
    }
}

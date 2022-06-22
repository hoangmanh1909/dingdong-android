package com.ems.dingdong.functions.mainhome.profile.ewallet.linkwallet;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.LinkEWalletResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.VerifyLinkOtpResult;
import com.ems.dingdong.model.request.PayLinkConfirm;
import com.ems.dingdong.model.request.PayLinkRequest;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;

import java.util.List;

import io.reactivex.Single;

public interface LinkEWalletContract {
    interface Interactor extends IInteractor<Presenter> {

        Single<LinkEWalletResult> linkEWallet(PayLinkRequest payLinkRequest);

        Single<VerifyLinkOtpResult> verifyLinkWithOtp(PayLinkConfirm payLinkConfirm);

        Single<SimpleResult> getDanhSachNganHang();
    }

    interface View extends PresentView<Presenter> {

        void showLinkSuccess(String message);

        void showOtpSuccess(String message);

        void showLinkError(String message);

        void showOtpError(String message);

        void showDanhSach(List<DanhSachNganHangRepsone> list);

    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getDanhSachNganHang();

        void linkEWallet(int type);

        void verifyLinkWithOtp(String requestId, String otp, int type);

        String getPhoneNumber();

        String getUserIdApp();
    }
}

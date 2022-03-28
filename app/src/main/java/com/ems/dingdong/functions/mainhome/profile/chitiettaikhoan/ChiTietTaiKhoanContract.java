package com.ems.dingdong.functions.mainhome.profile.chitiettaikhoan;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankInquiryBalanceRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.model.thauchi.YeuCauLienKetRequest;

import io.reactivex.Single;

public interface ChiTietTaiKhoanContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> ddHuyLienKet(SmartBankRequestCancelLinkRequest request);

        Single<SimpleResult> ddXacnhanhuy(SmartBankConfirmCancelLinkRequest request);

        Single<SimpleResult> ddTruyVanSodu(SmartBankInquiryBalanceRequest request);

        Single<SimpleResult> vnpCallOTP(CallOTP callOTP);

        Single<SimpleResult> ddSodu(SmartBankInquiryBalanceRequest request);

        Single<SimpleResult> ddTaiKhoanMacDinh(TaiKhoanMatDinh request);


    }

    interface View extends PresentView<Presenter> {
        void showOTP();
        void dissOTP();

        void huyLKThanhCong();

        void showSoDu(String sodu);

        void capNhatMacDinh();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void ddHuyLienKet(SmartBankRequestCancelLinkRequest request);

        void ddXacnhanhuy(SmartBankConfirmCancelLinkRequest request);

        void ddTruyVanSodu(SmartBankInquiryBalanceRequest request);

        void ddCallOTP(CallOTP request);

        void ddTaiKhoanMacDinh(TaiKhoanMatDinh request);

        void moveToEWallet();

    }
}

package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.DanhSachTaiKhoanRequest;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.model.thauchi.YeuCauLienKetRequest;

import io.reactivex.Single;

public interface ListBankContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> vnpCallOTP(CallOTP callOTP);

        Single<SimpleResult> smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request);
    }

    interface View extends PresentView<Presenter> {
        void showOTP();

        void showThanhCong();
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showEwallet();

        void taikhoanthauchi();

        void showSeaBank();

        void ddCallOTP(CallOTP request);

        void smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request);
    }
}

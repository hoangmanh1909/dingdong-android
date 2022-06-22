package com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.DanhSachTaiKhoanRequest;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.model.thauchi.YeuCauLienKetRequest;

import io.reactivex.Single;

public interface ListBankContract {

    interface Interactor extends IInteractor<Presenter> {
        Single<SimpleResult> vnpCallOTP(CallOTP callOTP);

        Single<SimpleResult> smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request);

        Single<SimpleResult> getDDsmartBankConfirmLinkRequest(BaseRequestModel request);
    }

    interface View extends PresentView<Presenter> {
        void showOTP();

        void showThanhCong();

        void dissmisOTP();


        void setsmartBankConfirmLink(String x);
    }

    interface Presenter extends IPresenter<View, Interactor> {

        void showEwallet();

        void showBank();

        void showSeaBank(SmartBankLink s);

        void ddCallOTP(CallOTP request);

        void smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request);

        void getDDsmartBankConfirmLinkRequest(BaseRequestModel x);
    }
}

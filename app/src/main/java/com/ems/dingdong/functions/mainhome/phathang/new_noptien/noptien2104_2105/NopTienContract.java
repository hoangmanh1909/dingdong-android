package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.PaymentRequestResponse;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class NopTienContract {
    interface Interactor extends IInteractor<Presenter> {
        Single<EWalletDataResult> getDataPayment(String serviceCode, String fromDate, String toDate, String poCode,
                                                 String routeCode, String postmanCode);



        Single<SimpleResult> getDanhSachNganHang();


        Single<SimpleResult> getDDsmartBankConfirmLinkRequest(BaseRequestModel request);

        Single<PaymentRequestResponse> requestPayment(PaymentRequestModel paymentRequestModel);

        Single<SimpleResult> deletePayment(DataRequestPayment dataRequestPayment);
    }

    interface View extends PresentView<Presenter> {
        void showListSuccess(List<EWalletDataResponse> eWalletDataResponses);

        void showConfirmError(String message);

        void showToast(String mess);

        void showDanhSach(ArrayList<DanhSachNganHangRepsone> list);

        void setsmartBankConfirmLink(String x);
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getDataPayment(String serviceCode, String poCode, String routeCode, String postmanCode, String fromDate, String toDate);

        void showLinkWalletFragment();


        void getDanhSachNganHang();

        String getCode();

        void getDDsmartBankConfirmLinkRequest(BaseRequestModel x);

        void deletePayment(List<EWalletDataResponse> list, String mobileNumber);

        void requestPayment(List<LadingPaymentInfo> list, String poCode,
                            String routeCode, String postmanCode, int type,
                            String bankcode, String posmanTel, String token, SmartBankLink item);


        void showOtp(List<LadingPaymentInfo> list, String otp, String requestId, String retRefNumber, String poCode,
                     String routeCode, String postmanCode, String mobileNumber, String token, int type, SmartBankLink bankcode);

        void showBarcode(BarCodeCallback barCodeCallback);

    }
}

package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PaymentPresenter extends Presenter<PaymentContract.View, PaymentContract.Interactor>
        implements PaymentContract.Presenter {

    private List<LadingPaymentInfo> ladingPaymentInfoList;

    public PaymentPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public PaymentContract.Interactor onCreateInteractor() {
        return new PaymentInteractor(this);
    }

    @Override
    public PaymentContract.View onCreateView() {
        return PaymentFragment.getInstance();
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }

    @Override
    public void showLinkWalletFragment() {
        new EWalletPresenter(mContainerView).pushView();
    }

    @Override
    public void getDataPayment(String poCode, String routeCode, String postmanCode, String fromDate, String toDate) {

        mView.showProgress();
        mInteractor.getDataPayment(fromDate, toDate, poCode, routeCode, postmanCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eWalletDataResult -> {
                    if (eWalletDataResult != null && eWalletDataResult.getErrorCode().equals("00")) {
                        mView.showListSuccess(eWalletDataResult.getListEWalletData());
                        mView.hideProgress();
                    }
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void requestPayment(List<EWalletDataResponse> list, String poCode, String routeCode, String postmanCode) {
        PaymentRequestModel requestModel = new PaymentRequestModel();
        SharedPref pref = SharedPref.getInstance(getViewContext());
        String token = pref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        ladingPaymentInfoList = new ArrayList<>();
        ladingPaymentInfoList.clear();
        for (EWalletDataResponse item : list) {
            LadingPaymentInfo info = new LadingPaymentInfo();
            info.setCodAmount(item.getCodAmount());
            info.setFeeCod(item.getFee());
            info.setLadingCode(item.getLadingCode());
            ladingPaymentInfoList.add(info);
        }
        requestModel.setLadingPaymentInfoList(ladingPaymentInfoList);
        requestModel.setPaymentToken(token);
        requestModel.setPoCode(poCode);
        requestModel.setPostmanCode(postmanCode);
        requestModel.setRouteCode(routeCode);
        mView.showProgress();
        mInteractor.requestPayment(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showRequestSuccess(simpleResult.getMessage(),
                                simpleResult.getListEWalletResponse().getTranid(),
                                simpleResult.getListEWalletResponse().getRetRefNumber());
                    } else if (simpleResult != null) {
                        mView.showConfirmError(simpleResult.getMessage());
                    } else {
                        mView.showConfirmError("Lỗi xử lí hệ thống, vui lòng liên hệ ban quản trị.");
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void confirmPayment(String otp, String requestId, String retRefNumber, String poCode,
                               String routeCode, String postmanCode) {
        PaymentConfirmModel model = new PaymentConfirmModel();
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String values = sharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
        String mobileNumber = "";
        if (!TextUtils.isEmpty(values)) {
            mobileNumber = values.split(";")[0];
        }
        String token = sharedPref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        model.setOtpCode(otp);
        model.setPaymentToken(token);
        model.setTransId(requestId);
        model.setRetRefNumber(retRefNumber);
        model.setPoCode(poCode);
        model.setRouteCode(routeCode);
        model.setPostmanCode(postmanCode);
        model.setLadingPaymentInfoList(ladingPaymentInfoList);
        model.setPostmanTel(mobileNumber);
        mView.showProgress();
        mInteractor.confirmPayment(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showConfirmSuccess(simpleResult.getMessage());
                    } else if (simpleResult != null) {
                        mView.showConfirmError(simpleResult.getMessage());
                    } else {
                        mView.showConfirmError("Lỗi xử lí hệ thống, vui lòng liên hệ ban quản trị.");
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }
}

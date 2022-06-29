package com.ems.dingdong.functions.mainhome.phathang.noptien;

import android.text.TextUtils;
import android.view.WindowManager;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.R;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13Presenter;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.huybaophat.CancelBD13TabContract;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListBaoPhatBangKePresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.listnganhang.ListBankPresenter;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletRemoveDataRequest;
import com.ems.dingdong.model.EWalletRemoveRequest;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.gson.Gson;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PaymentPresenter extends Presenter<PaymentContract.View, PaymentContract.Interactor>
        implements PaymentContract.Presenter {

    private int mPos;
    String id = "";
    String poCode = "";
    String Tranid = "";
    String RetRefNumber = "";
    String Mess = "";
    private List<LadingPaymentInfo> ladingPaymentInfoList;
    private List<EWalletRemoveRequest> removeRequests;
    private PaymentContract.OnTabListener tabListener;

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
    public void getDataPayment(String serviceCode, String poCode, String routeCode, String postmanCode, String fromDate, String toDate) {
        mView.showProgress();
        mInteractor.getDataPayment(serviceCode, fromDate, toDate, poCode, routeCode, postmanCode)
                .subscribeOn(Schedulers.io())
                .delay(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eWalletDataResult -> {
                    if (eWalletDataResult != null && eWalletDataResult.getErrorCode().equals("00")) {
                        mView.showListSuccess(eWalletDataResult.getListEWalletData());
                        mView.hideProgress();
                        if (eWalletDataResult.getListEWalletData().size() == 0) {
                            if (getPositionTab() == 0)
                                titleChanged(eWalletDataResult.getListEWalletData().size(), 0);
                            else if (getPositionTab() == 4)
                                titleChanged(eWalletDataResult.getListEWalletData().size(), 1);
//                            mView.showErrorToast("Không tìm thấy dữ liệu phù hợp");
                        }
                    } else {
                        mView.hideProgress();
                        Toast.showToast(getViewContext(), eWalletDataResult.getMessage());
                    }
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    OtpDialog otpDialog;

    @Override
    public void requestPayment(List<LadingPaymentInfo> list, String poCode,
                               String routeCode, String postmanCode, int type,
                               String bankcode, String posmanTel, String token, SmartBankLink item) {
        PaymentRequestModel requestModel = new PaymentRequestModel();
        SharedPref pref = SharedPref.getInstance(getViewContext());
//        String token = pref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        ladingPaymentInfoList = new ArrayList<>();
        ladingPaymentInfoList = list;
        requestModel.setLadingPaymentInfoList(list);
        requestModel.setPaymentToken(token);
        requestModel.setPoCode(poCode);
        requestModel.setPostmanCode(postmanCode);
        requestModel.setRouteCode(routeCode);
        requestModel.setBankCode(bankcode);
        requestModel.setPostmanTel(posmanTel);
        int account;
        if (item.getBankName().contains("MB")) {
            account = 2;
        } else account = 1;
        requestModel.setAccountType(account);
        if (getPositionTab() == 0)
            requestModel.setServiceCode("2104");
        else if (getPositionTab() == 4)
            requestModel.setServiceCode("2105");
        mView.showProgress();
        mInteractor.requestPayment(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        if (type == 1) {
//                            mView.showRequestSuccess(list, simpleResult.getMessage(),
//                                    simpleResult.getListEWalletResponse().getTranid(),
//                                    simpleResult.getListEWalletResponse().getRetRefNumber());
                            Tranid = simpleResult.getListEWalletResponse().getTranid();
                            RetRefNumber = simpleResult.getListEWalletResponse().getRetRefNumber();
                            Mess = simpleResult.getMessage();
                            otpDialog = new OtpDialog(getViewContext(), type, item, new OtpDialog.OnPaymentCallback() {
                                @Override
                                public void onPaymentClick(String otp) {
                                    confirmPayment(list, otp,
                                            Tranid, RetRefNumber, poCode, routeCode, postmanCode, posmanTel, token, account, bankcode);
                                }
                            }, Mess);
                            otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            otpDialog.show();
                            mView.hideProgress();
                        } else {
                            Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.showThanhCong();
                        }
                    } else if (simpleResult != null) {
                        mView.showConfirmError(simpleResult.getMessage());
                        mView.hideProgress();

                    } else {
                        mView.dongdialog();
                        mView.hideProgress();
                        mView.showConfirmError("Lỗi xử lí hệ thống, vui lòng liên hệ ban quản trị.");
                    }
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }


    @Override
    public void deletePayment(List<EWalletDataResponse> list) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        removeRequests = new ArrayList<>();
        removeRequests.clear();
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!TextUtils.isEmpty(userJson)) {
            id = NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD();
        }
        if (!TextUtils.isEmpty(postOfficeJson)) {
            poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        }
        for (EWalletDataResponse item : list) {
            EWalletRemoveRequest info = new EWalletRemoveRequest();
            info.setLadingCode(item.getLadingCode());
            if (item.getRetRefNumber() == null)
                info.setRetRefNumber("");
            else
                info.setRetRefNumber(item.getRetRefNumber());
            info.setRemoveBy(id);
            if (getPositionTab() == 0)
                info.setServiceCode("2104");
            else if (getPositionTab() == 4)
                info.setServiceCode("2105");

            info.setFeeType(Integer.parseInt(item.getFeeType()));
            info.setPOCode(poCode);
            removeRequests.add(info);
        }
        String dataJson = NetWorkController.getGson().toJson(removeRequests);
        dataRequestPayment.setData(dataJson);
        dataRequestPayment.setCode("EWL002");
        mView.showProgress();

        mInteractor.deletePayment(dataRequestPayment)
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

    @Override
    public void confirmPayment(List<LadingPaymentInfo> list, String otp, String requestId, String retRefNumber, String poCode,
                               String routeCode, String postmanCode, String mobileNumber, String token, int type, String bankcode) {
        PaymentConfirmModel model = new PaymentConfirmModel();
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String values = sharedPref.getString(Constants.KEY_MOBILE_NUMBER_SIGN_CODE, "");
//        if (!TextUtils.isEmpty(values)) {
//            mobileNumber = values.split(";")[0];
//        }
        model.setOtpCode(otp.toUpperCase());
        model.setPaymentToken(token);
        model.setTransId(requestId);
        model.setRetRefNumber(retRefNumber);
        model.setPoCode(poCode);
        model.setRouteCode(routeCode);
        model.setPostmanCode(postmanCode);
        model.setLadingPaymentInfoList(list);
        model.setPostmanTel(mobileNumber);
        model.setAccountType(type);
        model.setBankCode(bankcode);
        mView.showProgress();
        mInteractor.confirmPayment(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        mView.showConfirmSuccess(simpleResult.getMessage());
                        try {
                            otpDialog.dismiss();
                        } catch (Exception x) {
                            x.getMessage();
                        }
                    } else if (simpleResult.getErrorCode().equals("101")) {
                        mView.showConfirmError(simpleResult.getMessage());
                    } else {
                        mView.showConfirmError(simpleResult.getMessage());
                        try {
                            otpDialog.dismiss();
                        } catch (Exception x) {
                            x.getMessage();
                        }
                    }
                    mView.hideProgress();
                }, throwable -> {
                    try {
                        otpDialog.dismiss();
                    } catch (Exception x) {
                        x.getMessage();
                    }
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public int getPositionTab() {
        return mPos;
    }

    @Override
    public void showLienket() {
        new ListBankPresenter(mContainerView).pushView();
    }

    @Override
    public ContainerView getContainerView() {
        return mContainerView;
    }

    @Override
    public void onCanceled() {
        tabListener.onCanceledDelivery();
    }

    @Override
    public void cancelDelivery(DingDongCancelDeliveryRequest dingDongGetCancelDeliveryRequestList) {

    }

    @Override
    public void titleChanged(int quantity, int currentSetTab) {
        tabListener.onQuantityChange(quantity, currentSetTab);
    }

    @Override
    public int getCurrentTab() {
        return tabListener.getCurrentTab();
    }


    public PaymentPresenter setTypeTab(int position) {
        mPos = position;
        return this;
    }

    public PaymentPresenter setOnTabListener(PaymentContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }

    @Override
    public void getDDsmartBankConfirmLinkRequest(BaseRequestModel x) {
        mView.showProgress();
        mInteractor.getDDsmartBankConfirmLinkRequest(x)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null) {
                        if (simpleResult.getErrorCode().equals("00")) {
                            mView.setsmartBankConfirmLink(simpleResult.getData());
                        } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.hideProgress();
                    }
                }, Throwable::printStackTrace);
    }
}

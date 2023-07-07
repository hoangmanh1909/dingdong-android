package com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105;

import android.annotation.SuppressLint;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.new_noptien.noptien2104_2105.otp.OtpPresenter;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.model.BaseRequestModel;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.EWalletRemoveRequest;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.LadingCancelPaymentInfo;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentCancelRequestModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.SmartBankLink;
import com.ems.dingdong.model.thauchi.DanhSachNganHangRepsone;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NopTIenPresenter extends Presenter<NopTienContract.View, NopTienContract.Interactor>
        implements NopTienContract.Presenter {

    public NopTIenPresenter(ContainerView containerView) {
        super(containerView);
    }

    private String code;

    @Override
    public void start() {

    }

    @Override
    public NopTienContract.Interactor onCreateInteractor() {
        return new NopTienInteractor(this);
    }

    @Override
    public NopTienContract.View onCreateView() {
        return NopTienFragment.getInstance();
    }

    @SuppressLint("CheckResult")
    @Override
    public void getDataPayment(String serviceCode, String poCode, String routeCode, String postmanCode, String fromDate, String toDate) {
        mView.showProgress();
        mInteractor.getDataPayment(serviceCode, fromDate, toDate, poCode, routeCode, postmanCode)
                .subscribeOn(Schedulers.io())
                .delay(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<EWalletDataResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(EWalletDataResult simpleResult) {

                        if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                            EWalletDataResponse[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), EWalletDataResponse[].class);
                            List<EWalletDataResponse> list1 = Arrays.asList(list);
                            if (list1.size() == 0)
                                mView.showConfirmError("Không có dữ liệu nộp tiền " + code);
                            else mView.showListSuccess(list1);
                            mView.hideProgress();
                        } else {
                            mView.hideProgress();
                            mView.showConfirmError(simpleResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void showLinkWalletFragment() {
        new EWalletPresenter(mContainerView).pushView();
    }


    @Override
    public String getCode() {
        return code;
    }

    public NopTIenPresenter setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public void getDanhSachNganHang() {
        try {
            mView.showProgress();
            mInteractor.getDanhSachNganHang()
                    .delay(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(simpleResult -> {
                        if (simpleResult != null) {
                            if (simpleResult.getErrorCode().equals("00")) {
                                SharedPref sharedPref = new SharedPref((Context) mContainerView);
                                sharedPref.putString(Constants.KEY_LIST_BANK, simpleResult.getData());
                                ArrayList<DanhSachNganHangRepsone> list = NetWorkController.getGson().fromJson(simpleResult.getData(), new TypeToken<ArrayList<DanhSachNganHangRepsone>>() {
                                }.getType());
                                mView.showDanhSach(list);
                                mView.hideProgress();
                            } else Toast.showToast(getViewContext(), simpleResult.getMessage());
                            mView.hideProgress();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    @Override
    public void requestPayment(List<LadingPaymentInfo> list, String poCode,
                               String routeCode, String postmanCode, int type,
                               String bankcode, String posmanTel, String token, SmartBankLink item) {
        PaymentRequestModel requestModel = new PaymentRequestModel();
        SharedPref pref = SharedPref.getInstance(getViewContext());
//        String token = pref.getString(Constants.KEY_PAYMENT_TOKEN, "");
        List<LadingPaymentInfo> ladingPaymentInfoList = new ArrayList<>();
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
        if (code.equals(Constants.NOPTIEN_2104))
            requestModel.setServiceCode(Constants.NOPTIEN_2104);

        else if (code.equals(Constants.NOPTIEN_2105))
            requestModel.setServiceCode(Constants.NOPTIEN_2105);

        mView.showProgress();
        mInteractor.requestPayment(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getCode().equals("00")) {
                        if (type == 1) {
//                            mView.showRequestSuccess(list, simpleResult.getMessage(),
//                                    simpleResult.getListEWalletResponse().getTranid(),
//                                    simpleResult.getListEWalletResponse().getRetRefNumber());
//                            Tranid = simpleResult.getValue().getTranid();
//                            RetRefNumber = simpleResult.getValue().getRetRefNumber();
//                            Mess = simpleResult.getMessage();
//                            otpDialog = new OtpDialog(getViewContext(), type, item, new OtpDialog.OnPaymentCallback() {
//                                @Override
//                                public void onPaymentClick(String otp) {
//                                    confirmPayment(list, otp,
//                                            Tranid, RetRefNumber, poCode, routeCode, postmanCode, posmanTel, token, account, bankcode);
//                                }
//                            }, Mess);
//                            otpDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                            otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                            otpDialog.show();
                            showOtp(list, "", simpleResult.getValue().getTranid(), simpleResult.getValue().getRetRefNumber(),
                                    poCode, routeCode, postmanCode, posmanTel, token, account, item);
                            mView.hideProgress();
                        } else {
                            mView.showToast(simpleResult.getMessage());
                        }
                    } else if (simpleResult != null) {
                        mView.showToast(simpleResult.getMessage());
                        mView.hideProgress();

                    } else {
                        mView.hideProgress();
                        mView.showToast("Lỗi xử lí hệ thống, vui lòng liên hệ ban quản trị.");
                    }
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void showOtp(List<LadingPaymentInfo> list, String otp, String requestId, String retRefNumber, String poCode,
                        String routeCode, String postmanCode, String mobileNumber, String token, int type, SmartBankLink bankcode) {
        new OtpPresenter(mContainerView).setList(list).setType(type).setRequestId(requestId).setRetRefNumber(retRefNumber).setPoCode(poCode).setRouteCode(routeCode)
                .setToken(token).setBankcode(bankcode).setPostmanCode(postmanCode).setMobileNumber(mobileNumber).pushView();
    }

    @Override
    public void deletePayment(List<EWalletDataResponse> list, String mobileNumber) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
        List<EWalletRemoveRequest> removeRequests = new ArrayList<>();
        SharedPref sharedPref = SharedPref.getInstance(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        for (EWalletDataResponse item : list) {
            EWalletRemoveRequest info = new EWalletRemoveRequest();
            info.setLadingCode(item.getLadingCode());
            if (item.getRetRefNumber() == null)
                info.setRetRefNumber("");
            else
                info.setRetRefNumber(item.getRetRefNumber());
            info.setRemoveBy(NetWorkController.getGson().fromJson(userJson, UserInfo.class).getiD());
            info.setServiceCode(code);

            info.setFeeType(Integer.parseInt(item.getFeeType()));
            info.setPOCode(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode());
            info.setPostmanTel(mobileNumber);
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
                        mView.showToast(simpleResult.getMessage());
                    } else if (simpleResult != null) {
                        mView.showToast(simpleResult.getMessage());
                    } else {
                        mView.showToast("Lỗi xử lí hệ thống, vui lòng liên hệ ban quản trị.");
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void showBarcode(BarCodeCallback barCodeCallback) {
        new ScannerCodePresenter(mContainerView).setDelegate(barCodeCallback).pushView();
    }



}

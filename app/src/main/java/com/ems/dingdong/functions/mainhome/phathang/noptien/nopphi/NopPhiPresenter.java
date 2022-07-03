package com.ems.dingdong.functions.mainhome.phathang.noptien.nopphi;

import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.callback.BarCodeCallback;
import com.ems.dingdong.functions.mainhome.phathang.scanner.ScannerCodePresenter;
import com.ems.dingdong.functions.mainhome.profile.ewallet.EWalletPresenter;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.EWalletRemoveRequest;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NopPhiPresenter extends Presenter<NopPhiContract.View, NopPhiContract.Interactor>
        implements NopPhiContract.Presenter {

    private int mPos;
    String id = "";
    String poCode = "";
    String Tranid = "";
    String RetRefNumber = "";
    String Mess = "";
    private List<LadingPaymentInfo> ladingPaymentInfoList;
    private List<EWalletRemoveRequest> removeRequests;
    private NopPhiContract.OnTabListener tabListener;

    public NopPhiPresenter(ContainerView containerView) {
        super(containerView);
    }

    @Override
    public void start() {

    }

    @Override
    public NopPhiContract.Interactor onCreateInteractor() {
        return new NopPhiInteractor(this);
    }

    @Override
    public NopPhiContract.View onCreateView() {
        return NopPhiFragment.getInstance();
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
                        if (eWalletDataResult.getListEWalletData().size() == 0) {
                            titleChanged(eWalletDataResult.getListEWalletData().size(), 0);
                            mView.showErrorToast("Không tìm thấy dữ liệu phù hợp");
                        }
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
                    if (simpleResult != null && simpleResult.getCode().equals("00")) {
                        mView.showRequestSuccess(simpleResult.getMessage(),
                                simpleResult.getValue().getTranid(),
                                simpleResult.getValue().getRetRefNumber());
                        Tranid = simpleResult.getValue().getTranid();
                        RetRefNumber = simpleResult.getValue().getRetRefNumber();
                        Mess = simpleResult.getMessage();
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
    public void deletePayment(List<EWalletDataResponse> list,String mobileNumber) {
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
//            Log.d("thanhkhiempy", info.getRetRefNumber());
            info.setRemoveBy(id);
            info.setPOCode(poCode);
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
                    } else if (simpleResult != null && simpleResult.getErrorCode().equals("101")) {
                        Toast.showToast(getViewContext(), simpleResult.getMessage());
                        mView.showRequestSuccess(Mess,
                                Tranid,
                                RetRefNumber);
                    } else if (simpleResult != null) {
                        mView.showConfirmError(simpleResult.getMessage());
                    } else {
                        mView.showConfirmError("Lỗi xử lí hệ thống, vui lòng liên hệ ban quản trị.");
                    }
                    mView.dongdialog();
                    mView.hideProgress();
                }, throwable -> {
                    mView.showConfirmError(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public int getPositionTab() {
        return mPos;
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


    public NopPhiPresenter setTypeTab(int position) {
        mPos = position;
        return this;
    }

    public NopPhiPresenter setOnTabListener(NopPhiContract.OnTabListener listener) {
        this.tabListener = listener;
        return this;
    }

}

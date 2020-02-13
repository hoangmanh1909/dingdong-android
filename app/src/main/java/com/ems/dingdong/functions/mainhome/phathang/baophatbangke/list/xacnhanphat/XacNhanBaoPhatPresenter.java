package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class XacNhanBaoPhatPresenter extends Presenter<XacNhanBaoPhatContract.View, XacNhanBaoPhatContract.Interactor> implements XacNhanBaoPhatContract.Presenter {
    List<DeliveryPostman> mBaoPhatBangke;
    private Calendar calDate;
    private int mHour;
    private int mMinute;

    public XacNhanBaoPhatPresenter(ContainerView containerView) {
        super(containerView);
    }

    public XacNhanBaoPhatPresenter setBaoPhatBangKe(List<DeliveryPostman> baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);
        return this;
    }

    @Override
    public List<DeliveryPostman> getBaoPhatBangke() {
        return mBaoPhatBangke;
    }

    @Override
    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<ReasonResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<ReasonResult> call, Response<ReasonResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.getReasonsSuccess(response.body().getReasonInfos());
                }
            }

            @Override
            protected void onError(Call<ReasonResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void loadSolution(String code) {
        mInteractor.getSolutionByReasonCode(code, new CommonCallback<SolutionResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SolutionResult> call, Response<SolutionResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.showSolution(response.body().getSolutionInfos());
                }
            }

            @Override
            protected void onError(Call<SolutionResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void postImage(String path) {
        mView.showProgress();
        mInteractor.postImage(path, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                mView.showImage(response.body().getFile());
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog(message);
                mView.deleteFile();
            }
        });
    }

    @Override
    public void submitToPNS(String reason, String solution, String note, String sign) {

    }

    @Override
    public void paymentDelivery(String deliveryImage,String signCapture) {
        String postmanID = "";
        String mobileNumber = "";
        String deliveryPOCode = "";

        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");

        if (!userJson.isEmpty()) {
            UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            postmanID = userInfo.getiD();
            mobileNumber = userInfo.getMobileNumber();
        }
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        if (!postOfficeJson.isEmpty()) {
            PostOffice postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);

            deliveryPOCode = postOffice.getCode();
        }

        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");
        String routeCode = "";
        if (!postOfficeJson.isEmpty()) {
            RouteInfo routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
            routeCode = routeInfo.getRouteCode();
        }

        String deliveryDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";

        mView.showProgress();
        for (DeliveryPostman item : mBaoPhatBangke) {
            String parcelCode = item.getMaE();
            String receiverName = item.getReciverName();

            String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            String note = "";
            String amount = Integer.toString(item.getAmount());

            final String paymentChannel = "1";
            String deliveryType = "";
            String ladingPostmanID = Integer.toString(item.getId());
            String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
            String shiftId = Integer.toString(item.getShiftId());

            PaymentDeviveryRequest request = new PaymentDeviveryRequest(
                    postmanID,
                    parcelCode,
                    mobileNumber,
                    deliveryPOCode,
                    deliveryDate,
                    deliveryTime,
                    receiverName,
                    "",
                    reasonCode,
                    solutionCode,
                    status,
                    paymentChannel,
                    deliveryType,
                    signature,
                    note,
                    amount,
                    shiftId,
                    routeCode,
                    ladingPostmanID,
                    signature,
                    deliveryImage
            );

            mInteractor.paymentDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.hideProgress();
                        mView.showSuccess(response.body().getErrorCode());
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mView.hideProgress();
                    mView.showError(message);
                }
            });
        }
    }

    @Override
    public void start() {

    }

    @Override
    public XacNhanBaoPhatContract.Interactor onCreateInteractor() {
        return new XacNhanBaoPhatInteractor(this);
    }

    @Override
    public XacNhanBaoPhatContract.View onCreateView() {
        return XacNhanBaoPhatFragment.getInstance();
    }
}

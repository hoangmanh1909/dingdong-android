package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class XacNhanBaoPhatPresenter extends Presenter<XacNhanBaoPhatContract.View, XacNhanBaoPhatContract.Interactor> implements XacNhanBaoPhatContract.Presenter {
    List<DeliveryPostman> mBaoPhatBangke;
    private Calendar calDate;
    private int mHour;
    private int mMinute;
    RouteInfo routeInfo;
    UserInfo userInfo;
    PostOffice postOffice;

    public XacNhanBaoPhatPresenter(ContainerView containerView) {
        super(containerView);
    }

    public XacNhanBaoPhatPresenter setBaoPhatBangKe(List<DeliveryPostman> baoPhatBangKe) {
        this.mBaoPhatBangke = baoPhatBangKe;
        calDate = Calendar.getInstance();
        mHour = calDate.get(Calendar.HOUR_OF_DAY);
        mMinute = calDate.get(Calendar.MINUTE);


        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String routeJson = sharedPref.getString(Constants.KEY_ROUTE_INFO, "");

        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        }

        if (!postOfficeJson.isEmpty()) {
            postOffice = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class);
        }

        if (!routeJson.isEmpty()) {
            routeInfo = NetWorkController.getGson().fromJson(routeJson, RouteInfo.class);
        }

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
    public void submitToPNS(String reason, String solution, String note, String deliveryImage, String signCapture) {
        String postmanID =  userInfo.getiD();
        String deliveryPOSCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();

        for (DeliveryPostman item : mBaoPhatBangke) {

            String ladingCode = item.getMaE();
            String deliveryPOCode = deliveryPOSCode;
            String deliveryDate = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
            String deliveryTime = DateTimeUtils.convertDateToString(new Date(), DateTimeUtils.SIMPLE_DATE_FORMAT6);
            String receiverName = item.getReciverName();
            String reasonCode = reason;
            String solutionCode = solution;
            String status = "C18";
            String amount = Integer.toString(item.getAmount());
            String shiftId = Integer.toString(item.getShiftId());

            String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();

            PushToPnsRequest request = new PushToPnsRequest(
                    postmanID,
                    ladingCode,
                    deliveryPOCode,
                    deliveryDate,
                    deliveryTime,
                    receiverName,
                    reasonCode,
                    solutionCode,
                    status,
                    "",
                    "",
                    signCapture,
                    note,
                    amount,
                    Integer.toString(item.getId()),
                    shiftId,
                    routeCode,
                    signature,
                    deliveryImage);
            mInteractor.pushToPNSDelivery(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.showSuccess(response.body().getErrorCode());
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mView.showError(message);
                }
            });

        }
    }

    @Override
    public void paymentDelivery(String deliveryImage, String signCapture) {
        String postmanID = userInfo.getiD();
        String mobileNumber = userInfo.getMobileNumber();
        String deliveryPOCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();
        String deliveryDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";

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
                    mView.showSuccess(response.body().getErrorCode());
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mView.showError(message);
                }
            });
        }
    }

    @Override
    public void getRouteByPoCode(String poCode) {
        mInteractor.getRouteByPoCode(poCode, new CommonCallback<RouteInfoResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<RouteInfoResult> call, Response<RouteInfoResult> response) {
                super.onSuccess(call, response);
                mView.showRoute(response.body().getRouteInfos());
            }

            @Override
            protected void onError(Call<RouteInfoResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void getPostman(String poCode, int routeId, String routeType) {
        mInteractor.getPostman(poCode, routeId, routeType, new CommonCallback<UserInfoResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UserInfoResult> call, Response<UserInfoResult> response) {
                super.onSuccess(call, response);
                mView.showPostman(response.body().getUserInfos());
            }

            @Override
            protected void onError(Call<UserInfoResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void cancelDivided(int toRouteId, int toPostmanId,String signCapture,String fileImg) {
        List<DingDongCancelDividedRequest> requests = new ArrayList<>();

        for (DeliveryPostman item : mBaoPhatBangke)
        {
            DingDongCancelDividedRequest request = new DingDongCancelDividedRequest();
            request.setAmndPOCode(userInfo.getUnitCode());
            request.setAmndEmp(Integer.parseInt(userInfo.getiD()));
            request.setLadingCode(item.getMaE());
            request.setFromDeliveryRouteId(item.getRouteId());
            request.setFromPostmanId(item.getId());
            request.setToDeliveryRouteId(toRouteId);
            request.setToPostmanId(toPostmanId);
            request.setDescription("");
            request.setSignatureCapture(signCapture);
            request.setImageDelivery(fileImg);
            requests.add(request);
        }

        mInteractor.cancelDivided(requests,new CommonCallback<SimpleResult>((Context) mContainerView){
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.showCancelDivided(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showCancelDivided(message);
            }
        });
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

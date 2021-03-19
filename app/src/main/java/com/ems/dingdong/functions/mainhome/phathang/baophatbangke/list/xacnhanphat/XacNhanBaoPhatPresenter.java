package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryConstract;
import com.ems.dingdong.model.DeliveryListRelease;
import com.ems.dingdong.model.DeliveryListReturn;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DeliveryProductRequest;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.InfoVerify;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.DeliveryPaymentV2;
import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.response.DeliveryCheckAmountPaymentResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class XacNhanBaoPhatPresenter extends Presenter<XacNhanBaoPhatContract.View, XacNhanBaoPhatContract.Interactor> implements XacNhanBaoPhatContract.Presenter {
    List<DeliveryPostman> mBaoPhatBangke;
    List<DeliveryListRelease> mListRelease;
    private Calendar calDate;
    private int mHour;
    private int mMinute;
    private RouteInfo routeInfo;
    private UserInfo userInfo;
    private PostOffice postOffice;
    private ListDeliveryConstract.OnTabsListener titleTabsListener;
    private List<DeliveryCheckAmountPaymentResponse> paymentResponses;
    private List<PaypostPaymentRequest> paymentRequests;
    private UploadSingleResult uploadSingleResult;
    private List<DeliveryProductRequest> deliveryProductRequest;
    private List<DeliveryListRelease> deliveryListReleases;
    private List<DeliveryListReturn> deliveryListReturns;

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
    public List<DeliveryListRelease> getListProduct() {
        return mListRelease;
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
                if (response.body() != null) {
                    mView.showImage(response.body().getFile(), path);
                }
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                try {
                    mView.showAlertDialog("Không kết nối được với hệ thống");
                    mView.deleteFile();
                } catch (Exception exception) {
                }
            }
        });
    }

    @Override
    public void postImageAvatar(String pathAvatar) {
        mView.showProgress();
        mInteractor.postImageAvatar(pathAvatar, new CommonCallback<UploadSingleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<UploadSingleResult> call, Response<UploadSingleResult> response) {
                super.onSuccess(call, response);
                if (response.body() != null) {
                    mView.showImage(response.body().getFile(), pathAvatar);
                }
            }

            @Override
            protected void onError(Call<UploadSingleResult> call, String message) {
                super.onError(call, message);
                mView.showAlertDialog("Không kết nối được với hệ thống");
                mView.deleteFile();
            }
        });
    }


    @Override
    public void submitToPNS(String reason, String solution, String note, String deliveryImage, String authenImage, String signCapture) {
        String postmanID = userInfo.getiD();
        String deliveryPOSCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();

        for (DeliveryPostman item : mView.getItemSelected()) {

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
                    deliveryImage,
                    item.isItemReturn(),
                    item.getBatchCode(),
                    item.getItemsInBatch(),
                    item.getAmountForBatch());
            request.setCustomerCode(item.getCustomerCode());
            request.setVATCode(item.getVatCode());
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
    public void deliveryPartial(String deliveryImage, String imageAuthen, String imageReturn, String content, long amountRelease, long amountReturn,
                                String signCapture, String newReceiverName, String relationship, InfoVerify infoVerify,
                                List<DeliveryListRelease> listRelease, List<DeliveryListRelease> listReturn) {

        deliveryProductRequest = new ArrayList<>();
        deliveryListReleases = new ArrayList<>();
        deliveryListReturns = new ArrayList<>();
        String postmanID = userInfo.getiD();
        String mobileNumber = userInfo.getMobileNumber();
        String deliveryPOCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();
        String deliveryDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        boolean isPaymentPP = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false);
        for (DeliveryPostman item : mView.getItemSelected()) {
            String receiverName;
            //receiverName = item.getReciverName();
            if (newReceiverName.isEmpty()) {
                receiverName = item.getReciverName();
            } else {
                receiverName = newReceiverName;
            }
            String parcelCode = item.getMaE();
            String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            String note = "";

            final String paymentChannel = "1";
            String deliveryType = "";
            String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();

            DeliveryListRelease deliveryListRelease = new DeliveryListRelease();
            /*deliveryListProduct.setProductId();
            deliveryListProduct.setProductName();
            deliveryListProduct.setQuantity();
            deliveryListProduct.setUnitName();
            deliveryListProduct.setWeight();
            deliveryListProduct.setPrice();
            deliveryListProduct.setAmount();
            deliveryListProduct.setLadingToPostmanId();
            deliveryListProduct.setLadingCode();
            deliveryListProduct.setpODeliveryCode();
            deliveryListProducts.add(deliveryListProduct);*/

            for (DeliveryListRelease items : mView.getProductRelease()) {
                deliveryListRelease.setProductId(items.getProductId());
                deliveryListRelease.setProductName(items.getProductName());
                deliveryListRelease.setQuantity(items.getQuantity());
                deliveryListRelease.setUnitName(items.getUnitName());
                deliveryListRelease.setWeight(items.getWeight());
                deliveryListRelease.setPrice(items.getPrice());
                deliveryListRelease.setAmounts(items.getAmounts());
                deliveryListRelease.setLadingToPostmanId(items.getLadingToPostmanId());
                deliveryListRelease.setLadingCode(items.getLadingCode());
                deliveryListRelease.setpODeliveryCode(items.getpODeliveryCode());
                deliveryListReleases.add(deliveryListRelease);
                Log.d("123123", "deliveryListRelease.getQuantity() release: "+deliveryListRelease.getQuantity());
            }

            DeliveryListReturn deliveryListReturn = new DeliveryListReturn();
            /*deliveryListReturn.setProductId();
            deliveryListReturn.setProductName();
            deliveryListReturn.setQuantity();
            deliveryListReturn.setUnitName();
            deliveryListReturn.setWeight();
            deliveryListReturn.setPrice();
            deliveryListReturn.setAmount();
            deliveryListReturn.setLadingToPostmanId();
            deliveryListReturn.setLadingCode();
            deliveryListReturn.setpODeliveryCode();
            deliveryListReturns.add(deliveryListReturn);*/

            for (DeliveryListRelease items : mView.getProductReturn()) {
                deliveryListReturn.setProductId(items.getProductId());
                deliveryListReturn.setProductName(items.getProductName());
                deliveryListReturn.setQuantity(items.getQuantity());
                deliveryListReturn.setUnitName(items.getUnitName());
                deliveryListReturn.setWeight(items.getWeight());
                deliveryListReturn.setPrice(items.getPrice());
                deliveryListReturn.setAmounts(items.getAmounts());
                deliveryListReturn.setLadingToPostmanId(items.getLadingToPostmanId());
                deliveryListReturn.setLadingCode(items.getLadingCode());
                deliveryListReturn.setpODeliveryCode(items.getpODeliveryCode());
                deliveryListReturns.add(deliveryListReturn);
                Log.d("123123", "deliveryListReturn.getQuantity() return: "+deliveryListReturn.getQuantity());
            }

            //DeliveryListRelease itemPartialRelease = mListRelease.get(position);



            DeliveryProductRequest request = new DeliveryProductRequest();
            request.setReturnImage(imageReturn);
            request.setDeliveryAmount(amountRelease);
            request.setReturnAmount(amountReturn);
            request.setPostmanID(postmanID);
            request.setParcelCode(parcelCode);
            request.setMobileNumber(mobileNumber);
            request.setDeliveryPOCode(deliveryPOCode);
            request.setDeliveryDate(deliveryDate);
            request.setDeliveryTime(deliveryTime);
            request.setReceiverName(receiverName);
            request.setReasonCode(reasonCode);
            request.setSolutionCode(solutionCode);
            request.setStatus(status);
            request.setPaymentChannel(paymentChannel);
            request.setSignatureCapture(signCapture);
            request.setNote(note);
            request.setCollectAmount(item.getAmount());
            request.setShiftID(item.getShiftId());
            request.setRouteCode(routeCode);
            request.setLadingPostmanID(item.getId());
            request.setSignature(signature);
            request.setImageDelivery(deliveryImage);
            request.setBatchCode(item.getBatchCode());
            request.setIsItemReturn(item.isItemReturn());
            request.setItemsInBatch(item.getItemsInBatch());
            request.setAmountForBatch(item.getAmountForBatch());
            request.setReceiverReference(relationship);
            request.setPaymentPP(isPaymentPP);
            request.setReplaceCode(item.getReplaceCode());
            request.setRePaymentBatch(item.isRePaymentBatch());
            request.setLastLadingCode(item.getLastLadingCode());
            request.setPaymentBatch(item.isPaymentBatch());
            request.setPostmanCode(userInfo.getUserName());
            request.setCustomerCode(item.getCustomerCode());
            request.setReceiverPIDWhere(infoVerify.getReceiverPIDWhere());
            request.setReceiverPIDDate(infoVerify.getReceiverPIDDate());
            request.setReceiverBirthday(infoVerify.getReceiverBirthday());
            request.setReceiverAddressDetail(infoVerify.getReceiverAddressDetail());
            request.setAuthenType(infoVerify.getAuthenType());
            request.setReceiverIDNumber(infoVerify.getGtgt());
            request.setImageAuthen(imageAuthen);
            request.setVATCode(item.getVatCode());
            request.setListProduct(deliveryListReleases);
            request.setListReturn(deliveryListReturns);
            deliveryProductRequest.add(request);

            //
            mInteractor.deliveryPartial(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    //mView.showSuccessPartial(response.body().getErrorCode());
                    Log.d("123123", "onSuccess");
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    //mView.showSuccessPartial(message);
                    Log.d("123123", "onError");
                }
            });
        }


    }

    @Override
    public void paymentDelivery(String deliveryImage, String imageAuthen, String signCapture, String newReceiverName,
                                String relationship, InfoVerify infoVerify) {
        paymentRequests = new ArrayList<>();
        String postmanID = userInfo.getiD();
        String mobileNumber = userInfo.getMobileNumber();
        String deliveryPOCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();
        String deliveryDate = DateTimeUtils.convertDateToString(calDate.getTime(), DateTimeUtils.SIMPLE_DATE_FORMAT5);
        String deliveryTime = (mHour < 10 ? "0" + mHour : mHour + "") + (mMinute < 10 ? "0" + mMinute : mMinute + "") + "00";
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        boolean isPaymentPP = sharedPref.getBoolean(Constants.KEY_GACH_NO_PAYPOS, false);
        for (DeliveryPostman item : mView.getItemSelected()) {
            String receiverName;
            //receiverName = item.getReciverName();
            if (newReceiverName.isEmpty()) {
                receiverName = item.getReciverName();
            } else {
                receiverName = newReceiverName;
            }
            String parcelCode = item.getMaE();
            String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            String note = "";

            final String paymentChannel = "1";
            String deliveryType = "";
            String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();

            PaypostPaymentRequest request = new PaypostPaymentRequest();
            request.setPostmanID(postmanID);
            request.setParcelCode(parcelCode);
            request.setMobileNumber(mobileNumber);
            request.setDeliveryPOCode(deliveryPOCode);
            request.setDeliveryDate(deliveryDate);
            request.setDeliveryTime(deliveryTime);
            request.setReceiverName(receiverName);
            request.setReasonCode(reasonCode);
            request.setSolutionCode(solutionCode);
            request.setStatus(status);
            request.setPaymentChannel(paymentChannel);
            request.setSignatureCapture(signCapture);
            request.setNote(note);
            request.setCollectAmount(item.getAmount());
            request.setShiftID(item.getShiftId());
            request.setRouteCode(routeCode);
            request.setLadingPostmanID(item.getId());
            request.setSignature(signature);
            request.setImageDelivery(deliveryImage);
            request.setBatchCode(item.getBatchCode());
            request.setIsItemReturn(item.isItemReturn());
            request.setItemsInBatch(item.getItemsInBatch());
            request.setAmountForBatch(item.getAmountForBatch());
            request.setReceiverReference(relationship);
            request.setPaymentPP(isPaymentPP);
            request.setReplaceCode(item.getReplaceCode());
            request.setRePaymentBatch(item.isRePaymentBatch());
            request.setLastLadingCode(item.getLastLadingCode());
            request.setPaymentBatch(item.isPaymentBatch());
            request.setPostmanCode(userInfo.getUserName());
            request.setCustomerCode(item.getCustomerCode());
            request.setReceiverPIDWhere(infoVerify.getReceiverPIDWhere());
            request.setReceiverPIDDate(infoVerify.getReceiverPIDDate());
            request.setReceiverBirthday(infoVerify.getReceiverBirthday());
            request.setReceiverAddressDetail(infoVerify.getReceiverAddressDetail());
            request.setAuthenType(infoVerify.getAuthenType());
            request.setReceiverIDNumber(infoVerify.getGtgt());
            request.setImageAuthen(imageAuthen);
            request.setVATCode(item.getVatCode());
            paymentRequests.add(request);
        }
        mInteractor.paymentDelivery(paymentRequests)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        simpleResult -> {
                            paymentResponses = simpleResult.getPaymentResponses();
                            if (simpleResult.getErrorCode().equals("01")) {
                                long amountPP = 0;
                                long amountPNS = 0;
                                if (paymentResponses != null) {
                                    for (DeliveryCheckAmountPaymentResponse item : paymentResponses) {
                                        amountPP += item.getPayPostAmount();
                                        amountPNS += item.getPNSAmount();
                                    }
                                }
                                mView.showCheckAmountPaymentError(
                                        simpleResult.getMessage(),
                                        NumberUtils.formatPriceNumber(amountPP),
                                        NumberUtils.formatPriceNumber(amountPNS));
                            } else {
                                mView.showPaymentV2Success(simpleResult.getMessage());
                            }
                        },
                        throwable -> {
                            mView.showPaymentV2Success(throwable.getMessage());
                        }
                );
    }

    @Override
    public void paymentV2(boolean isAutoUpdateCODAmount) {
        DeliveryPaymentV2 request = new DeliveryPaymentV2();
        request.setAutoUpdateCODAmount(isAutoUpdateCODAmount);
        request.setPaymentResponses(paymentResponses);
        request.setPaymentRequests(paymentRequests);
        mInteractor.paymentV2(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> mView.showPaymentV2Success(simpleResult.getMessage()),
                        throwable -> mView.showPaymentV2Success(throwable.getMessage()));
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
    public void cancelDivided(int toRouteId, int toPostmanId, String signCapture, String fileImg) {
        List<DingDongCancelDividedRequest> requests = new ArrayList<>();

        for (DeliveryPostman item : mView.getItemSelected()) {
            DingDongCancelDividedRequest request = new DingDongCancelDividedRequest();
            request.setAmndPOCode(userInfo.getUnitCode());
            request.setAmndEmp(Integer.parseInt(userInfo.getiD()));
            request.setLadingCode(item.getMaE());
            request.setFromDeliveryRouteId(item.getRouteId());
            request.setFromPostmanId(Integer.parseInt(userInfo.getiD()));
            request.setToDeliveryRouteId(toRouteId);
            request.setToPostmanId(toPostmanId);
            request.setDescription("");
            request.setSignatureCapture(signCapture);
            request.setImageDelivery(fileImg);
            requests.add(request);
        }

        mInteractor.cancelDivided(requests, new CommonCallback<SimpleResult>((Context) mContainerView) {
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
    public void changeRouteInsert(int toRouteId, int toPostmanId, String signCapture, String fileImg) {
        List<ChangeRouteRequest> requests = new ArrayList<>();

        for (DeliveryPostman item : mView.getItemSelected()) {
            ChangeRouteRequest request = new ChangeRouteRequest();
            request.setPoCode(postOffice.getCode());
            request.setLadingCode(item.getMaE());
            request.setFromRouteId(item.getRouteId());
            request.setPostmanId(Integer.parseInt(userInfo.getiD()));
            request.setToRouteId(toRouteId);
            request.setToPostmanId(toPostmanId);
            request.setDescription("");
            request.setSignatureCapture(signCapture);
            request.setImageDelivery(fileImg);
            requests.add(request);
        }

        mInteractor.changeRouteInsert(requests.get(0), new CommonCallback<SimpleResult>((Context) mContainerView) {
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
    public void onTabRefresh() {
        titleTabsListener.onDelivered();
    }

    @Override
    public void start() {

    }

    public XacNhanBaoPhatPresenter setOnTabChangeListener(ListDeliveryConstract.OnTabsListener listener) {
        titleTabsListener = listener;
        return this;
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

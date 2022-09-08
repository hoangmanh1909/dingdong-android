package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat;

import android.app.Activity;
import android.content.Context;

import com.core.base.viper.Presenter;
import com.core.base.viper.interfaces.ContainerView;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.ListDeliveryConstract;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.LadingProduct;
import com.ems.dingdong.model.DeliveryPostman;
import com.ems.dingdong.model.DeliverySuccessRequest;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.DistrictModels;
import com.ems.dingdong.model.InfoVerify;
import com.ems.dingdong.model.PostOffice;
import com.ems.dingdong.model.ReasonInfo;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfo;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionInfo;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfo;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.WardModels;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.DeliveryPaymentV2;
import com.ems.dingdong.model.request.DeliveryProductRequest;
import com.ems.dingdong.model.request.DeliveryUnSuccessRequest;
import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.response.DeliveryCheckAmountPaymentResponse;
import com.ems.dingdong.network.NetWorkController;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.DateTimeUtils;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.NumberUtils;
import com.ems.dingdong.utiles.SharedPref;
import com.ems.dingdong.utiles.Toast;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class XacNhanBaoPhatPresenter extends Presenter<XacNhanBaoPhatContract.View, XacNhanBaoPhatContract.Interactor> implements XacNhanBaoPhatContract.Presenter {
    List<DeliveryPostman> mBaoPhatBangke;
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

    public XacNhanBaoPhatPresenter(ContainerView containerView) {
        super(containerView);
    }

    public XacNhanBaoPhatPresenter setBaoPhatBangKe(List<DeliveryPostman> baoPhatBangKe) {
        for (int i = 0; i < baoPhatBangKe.size(); i++)
            if (!baoPhatBangKe.get(i).getVatCode().isEmpty()) {
                String gtgt[] = baoPhatBangKe.get(i).getVatCode().split(",");
                for (int j = 0; j < gtgt.length; j++) {
                    if (gtgt[j].equals("AHZ")) {
                        baoPhatBangKe.get(i).setItemReturn("N");
                        break;
                    }
                }
            }
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
    public void vietmapDecode(String decode, int posi) {
        mInteractor.vietmapSearchDecode(decode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult.getErrorCode().equals("00")) {
                        mBaoPhatBangke.get(posi).setReceiverLat(String.valueOf(simpleResult.getObject().getResult().getLocation().getLatitude()));
                        mBaoPhatBangke.get(posi).setReceiverLon(String.valueOf(simpleResult.getObject().getResult().getLocation().getLongitude()));
                    } else {
                        mBaoPhatBangke.get(posi).setReceiverLat("");
                        mBaoPhatBangke.get(posi).setReceiverLon("");
//                        mView.showError(simpleResult.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public List<DeliveryPostman> getBaoPhatBangke() {
        return mBaoPhatBangke;
    }

    @Override
    public void getReasons() {
        mInteractor.getReasons(new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    ArrayList<ReasonInfo> reasonInfos = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<ReasonInfo>>() {
                    }.getType());
                    mView.getReasonsSuccess(reasonInfos);
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
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
//                    ArrayList<SolutionInfo> solutionInfos = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<SolutionInfo>>() {
//                    }.getType());
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
                if (response.body().getErrorCode() != null) {
                    if (response.body().getErrorCode().equals("00"))
                        mView.showImage(response.body().getFile(), path);
                    else Toast.showToast(getViewContext(), response.body().getMessage());
                } else {
                    if (response.body().getMessage() == null)
                        mView.showAlertDialog("Không kết nối được với hệ thống!");
                    else mView.showAlertDialog(response.body().getMessage());
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
//                mView.deleteFile();
            }
        });
    }


    @Override
    public void submitToPNS(String reason, String solution, String note, String deliveryImage,
                            String authenImage, String signCapture,
                            String EstimateProcessTime, boolean ischeck, String lydo, int idXaPhuong, int idQuanhuyen) {
        mView.showProgress();
        Log.d("thanhkhieee", note);
        String postmanID = userInfo.getiD();
        String deliveryPOSCode = postOffice.getCode();
        String routeCode = routeInfo.getRouteCode();
        SharedPref sharedPref = new SharedPref(getViewContext());
        String postManCode1 = userInfo.getUserName();
        String postManTel1 = userInfo.getMobileNumber();

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
            boolean isCancle = ischeck;
            long feeCancle = item.getFeeCancelOrder();
            String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
//            if (isCancle)
            feeCancle = item.getFeeCancelOrder();
//            else feeCancle = 0;
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
                    item.getAmountForBatch(),
                    isCancle,
                    feeCancle,
                    postManTel1,
                    postManCode1,
                    item.getDeliveryLat(),
                    item.getDeliveryLon(),
                    item.getReceiverLat() == null ||item.getReceiverLat().isEmpty() ? 0.0 : Double.parseDouble(item.getReceiverLat()),
                    item.getReceiverLon() == null||item.getReceiverLon().isEmpty() ? 0.0 : Double.parseDouble(item.getReceiverLon()),
                    NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLat(),
                    NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLon(),
                    EstimateProcessTime, "DD_ANDROID", lydo);
            request.setDeliveryWardIdAdditional(idXaPhuong);
            request.setDeliveryDistrictIdAdditional(idQuanhuyen);
            request.setCustomerCode(item.getCustomerCode());
            request.setVATCode(item.getVatCode());

            DeliveryUnSuccessRequest deliveryUnSuccessRequest = new DeliveryUnSuccessRequest();
            deliveryUnSuccessRequest.setData(request);
            String bankCode = new String();
            if (userInfo.getSmartBankLink() != null)
                for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                    if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                        if (userInfo.getSmartBankLink() != null && userInfo.getSmartBankLink().get(i).getIsDefaultPayment()) {
                            bankCode = userInfo.getSmartBankLink().get(i).getBankCode();
                        }
                    }
                }
            deliveryUnSuccessRequest.setPaymentBankCode(bankCode);

            mInteractor.pushToDeliveryUnSuccess(deliveryUnSuccessRequest, new CommonCallback<SimpleResult>((Activity) mContainerView) {
                @Override
                protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                    super.onSuccess(call, response);
                    mView.showSuccess(response.body().getErrorCode(), deliveryUnSuccessRequest.getData().getLadingPostmanID());
                    mView.hideProgress();
                }

                @Override
                protected void onError(Call<SimpleResult> call, String message) {
                    super.onError(call, message);
                    mView.showError(message);
                    mView.hideProgress();
                }
            });

        }
    }

    @Override
    public void deliveryPartial(DeliveryProductRequest request) {
        mView.showProgress();
        mInteractor.deliveryPartial(request, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                if (response.body().getErrorCode().equals("00")) {
                    mView.hideProgress();
                    mView.showSuccess(response.body().getErrorCode(), "");
                } else mView.showError(response.body().getMessage());
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.showError(message);
            }
        });
    }

    @Override
    public void paymentDelivery(String deliveryImage, String imageAuthen, String signCapture, String newReceiverName,
                                String relationship, InfoVerify infoVerify, boolean isCod, long codeEdit, String note,
                                boolean IsExchange, String ExchangePODeliveryCode, String ExchangeRouteCode, String ExchangeLadingCode,
                                long ExchangeDeliveryDate, int ExchangeDeliveryTime, List<LadingProduct> ExchangeDetails,
                                String imgAnhHoangTra, int idXaphuong, int idQuanHUyen, String idCOD) {
        mView.showProgress();
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
            if (newReceiverName.isEmpty()) {
                receiverName = item.getReciverName();
            } else {
                receiverName = newReceiverName;
            }
            String parcelCode = item.getMaE();
            String reasonCode = "";
            String solutionCode = "";
            String status = "C14";
            String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
            final String paymentChannel = "1";
            String deliveryType = "";
            String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
            PaypostPaymentRequest request = new PaypostPaymentRequest();
            request.setDeliveryLat(item.getDeliveryLat());
            request.setDeliveryLon(item.getDeliveryLon());
            request.setReceiverLat(item.getReceiverLat() == null|| item.getReceiverLat().isEmpty() ? 0.0 : Double.parseDouble(item.getReceiverLat()));
            request.setReceiverLon(item.getReceiverLon() == null || item.getReceiverLon().isEmpty()? 0.0 : Double.parseDouble(item.getReceiverLon()));
            request.setPODeliveryLat(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLat());
            request.setPODeliveryLon(NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getPOLon());
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
            request.setItemsInBatch(item.getItemsInBatch());
            request.setIsItemReturn(item.isItemReturn());
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
            request.setFeeCollectLater(item.getFeeCollectLater());
            request.setFeeCollectLaterPNS(item.getFeeCollectLater());
            request.setFeePPA(item.getFeePPA());
            request.setFeePPAPNS(item.getFeePPA());
            request.setFeeShip(item.getFeeShip());
            request.setFeeShipPNS(item.getFeeShip());
            request.setEditCODAmount(isCod);
            request.setcODAmountEdit(codeEdit);
            request.setFeeCOD(item.getFeeCOD());
            request.setFeePA(item.getFeePA());
            request.setFeeC(item.getFeeC());

            request.setExchange(IsExchange);
            request.setExchangePODeliveryCode(ExchangePODeliveryCode);
            request.setExchangeRouteCode(ExchangeRouteCode);
            request.setExchangeLadingCode(ExchangeLadingCode);
            request.setExchangeDeliveryDate(ExchangeDeliveryDate);
            request.setExchangeDeliveryTime(ExchangeDeliveryTime);
            request.setExchangeDetails(ExchangeDetails);
            request.setImageExchange(imgAnhHoangTra);
            request.setDeliveryWardIdAdditional(idXaphuong);
            request.setDeliveryDistrictIdAdditional(idQuanHUyen);
            request.setEditCODAmountCallId(idCOD);

            request.setSourceChanel("DD_ANDROID");
            paymentRequests.add(request);
        }


        DeliverySuccessRequest deliverySuccessRequest = new DeliverySuccessRequest();

        String bankCode = new String();
        if (userInfo.getSmartBankLink() != null)
            for (int i = 0; i < userInfo.getSmartBankLink().size(); i++) {
                if (userInfo.getSmartBankLink().get(i).getBankCode().equals("SeABank")) {
                    if (userInfo.getSmartBankLink() != null && userInfo.getSmartBankLink().get(i).getIsDefaultPayment()) {
                        bankCode = userInfo.getSmartBankLink().get(i).getBankCode();
                    }
                }
            }
        deliverySuccessRequest.setPaymentBankCode(bankCode);
        deliverySuccessRequest.setPaypostPaymentRequests(paymentRequests);

        mInteractor.checkDeliverySuccess(deliverySuccessRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        simpleResult -> {
                            mView.hideProgress();
                            paymentResponses = simpleResult.getPaymentResponses();
                            if (simpleResult.getErrorCode().equals("01")) {
                                long amountPP = 0;
                                long amountPNS = 0;
                                if (paymentResponses != null) {
                                    for (DeliveryCheckAmountPaymentResponse item : paymentResponses) {
                                        amountPP += item.getPayPostAmount() + item.getFeeCollectLaterPP() + item.getFeePPAPNS() + item.getFeeShipPP() + item.getFeePA();
                                        amountPNS += item.getPNSAmount() + item.getFeePPAPNS() + item.getFeeShipPNS() + item.getFeeCollectLaterPNS() + item.getFeePAPNS();
                                    }
                                }
                                mView.showCheckAmountPaymentError(
                                        simpleResult.getMessage(),
                                        NumberUtils.formatPriceNumber(amountPP),
                                        NumberUtils.formatPriceNumber(amountPNS));
                            } else {
                                mView.showPaymentV2Success(simpleResult.getMessage(), simpleResult.getData());

                            }
                        },
                        throwable -> {
                            mView.hideProgress();
                            mView.showPaymentV2Error(throwable.getMessage());

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
                .subscribe(simpleResult -> mView.showPaymentV2Success(simpleResult.getMessage(), simpleResult.getData()),
                        throwable -> mView.showPaymentV2Error(throwable.getMessage()));
    }

    @Override
    public void getRouteByPoCode(String poCode) {
        mInteractor.getRouteByPoCode(poCode, new CommonCallback<RouteInfoResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<RouteInfoResult> call, Response<RouteInfoResult> response) {
                super.onSuccess(call, response);
//                ArrayList<RouteInfo> arrayList = NetWorkController.getGson().fromJson(response.body().getData(),new TypeToken<List<RouteInfo>>(){}.getType());
                try {
                    mView.showRoute(response.body().getRouteInfos());

                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            protected void onError(Call<RouteInfoResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void getPostman(String poCode, int routeId, String routeType) {
        mInteractor.getPostman(poCode, routeId, routeType, new CommonCallback<SimpleResult>((Context) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                ArrayList<UserInfo> userInfos = NetWorkController.getGson().fromJson(response.body().getData(), new TypeToken<List<UserInfo>>() {
                }.getType());
                mView.showPostman(userInfos);
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
            }
        });
    }

    @Override
    public void cancelDivided(String toPoCode, int toRouteId, int toPostmanId, String signCapture, String fileImg) {
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
            request.setToPOCode(toPoCode);
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
    public void onTabRefresh(String data, int mType) {
        titleTabsListener.onDelivered(data, mType);
    }

    @Override
    public void start() {
        for (int i = 0; i < mBaoPhatBangke.size(); i++) {
            if (!mBaoPhatBangke.get(i).getReceiverVpostcode().equals(""))
                vietmapDecode(mBaoPhatBangke.get(i).getReceiverVpostcode(), i);
        }
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

    @Override
    public void callForward(String phone, String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.callForwardCallCenter(callerNumber, phone, "1", hotline, parcelCode, userInfo.getiD(), poCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showCallSuccess(response.body().getData());
                    } else {
                        mView.showCallError(response.body().getMessage());
                    }
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showCallError(message);
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable error) {
                super.onFailure(call, error);
                mView.showCallError("Lỗi kết nối đến tổng đài");
            }
        });

    }

    @Override
    public void callForwardEditCOD(String phone, String parcelCode) {
        SharedPref sharedPref = new SharedPref((Context) mContainerView);
        String callerNumber = "";
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = null;
        String postOfficeJson = sharedPref.getString(Constants.KEY_POST_OFFICE, "");
        String poCode = NetWorkController.getGson().fromJson(postOfficeJson, PostOffice.class).getCode();
        if (!userJson.isEmpty()) {
            userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
            callerNumber = userInfo.getMobileNumber();
        }
        String hotline = sharedPref.getString(Constants.KEY_HOTLINE_NUMBER, "");
        mView.showProgress();
        mInteractor.CallForwardEditCOD(callerNumber, phone, "1", hotline, parcelCode, userInfo.getiD(), poCode, new CommonCallback<SimpleResult>((Activity) mContainerView) {
            @Override
            protected void onSuccess(Call<SimpleResult> call, Response<SimpleResult> response) {
                super.onSuccess(call, response);
                mView.hideProgress();
                if (response.body() != null) {
                    if (response.body().getErrorCode().equals("00")) {
                        mView.showCallEdit(response.body().getData());
                    } else {
                        mView.showCallError(response.body().getMessage());
                    }
                }
            }

            @Override
            protected void onError(Call<SimpleResult> call, String message) {
                super.onError(call, message);
                mView.hideProgress();
                mView.showCallError(message);
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable error) {
                super.onFailure(call, error);
                mView.showCallError("Lỗi kết nối đến tổng đài");
            }
        });
    }


    @Override
    public void getXaPhuong(int id) {
        mView.showProgress();
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        mInteractor.getXaPhuong(new BaseRequest(id, userInfo.getMobileNumber(), null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        WardModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), WardModels[].class);
                        List<WardModels> list1 = Arrays.asList(list);
                        mView.showXaPhuong(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }

    @Override
    public void getQuanHuyen(int id) {
        mView.showProgress();
        SharedPref sharedPref = new SharedPref(getViewContext());
        String userJson = sharedPref.getString(Constants.KEY_USER_INFO, "");
        UserInfo userInfo = NetWorkController.getGson().fromJson(userJson, UserInfo.class);
        mInteractor.getQuanHuyen(new BaseRequest(id, userInfo.getMobileNumber(), null, null))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(simpleResult -> {
                    if (simpleResult != null && simpleResult.getErrorCode().equals("00")) {
                        DistrictModels[] list = NetWorkController.getGson().fromJson(simpleResult.getData(), DistrictModels[].class);
                        List<DistrictModels> list1 = Arrays.asList(list);
                        mView.showQuanHuyen(list1);
                        mView.hideProgress();
                    }
                    mView.hideProgress();
                }, throwable -> {
                    mView.showErrorToast(throwable.getMessage());
                    mView.hideProgress();
                });
    }
}

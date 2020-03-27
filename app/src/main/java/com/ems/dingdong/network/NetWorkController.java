package com.ems.dingdong.network;


import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ActiveResult;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.CancelDeliveryResult;
import com.ems.dingdong.model.ChangeRouteResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.HistoryCallResult;
import com.ems.dingdong.model.HistoryCreateBd13Result;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.StatisticCollectResult;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.model.StatisticDebitGeneralResult;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.UploadResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.BankAccountNumberRequest;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.request.SeaBankInquiryRequest;
import com.ems.dingdong.model.request.SeaBankPaymentRequest;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.UpdateRequest;
import com.ems.dingdong.model.response.BankAccountNumberResponse;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;
import com.ems.dingdong.model.response.IdentifyCationResponse;
import com.ems.dingdong.model.response.SeaBankHistoryPaymentResponse;
import com.ems.dingdong.model.response.SeaBankInquiryResponse;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import static com.ems.dingdong.utiles.Utils.getUnsafeOkHttpClient;


public class NetWorkController {

    private NetWorkController() {
    }

    private static volatile VinattiAPI apiBuilder;
    private static volatile VinattiAPI apiRxBuilder;
    private static volatile ChiHoBtxhAPI chiHoBtxhAPI;


    public static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static ChiHoBtxhAPI getChiHoBtxhAPI() {
        if (chiHoBtxhAPI == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(120, 120))
                    .build();
            chiHoBtxhAPI = retrofit.create(ChiHoBtxhAPI.class);
        }
        return chiHoBtxhAPI;
    }

    private static VinattiAPI getAPIBuilder() {
        if (apiBuilder == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(120, 120))
                    .build();
            apiBuilder = retrofit.create(VinattiAPI.class);
        }
        return apiBuilder;
    }

    private static VinattiAPI getAPIRxBuilder() {
        if (apiRxBuilder == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(120, 120))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiRxBuilder = retrofit.create(VinattiAPI.class);
        }
        return apiRxBuilder;
    }


    public static void taskOfWork(SimpleResult taskRequest, CommonCallback<SimpleResult> callback) {

        Call<SimpleResult> call = getAPIBuilder().taskOfWork(taskRequest);
        call.enqueue(callback);
    }

    public static void addNewBD13(Bd13Create bd13Create, CommonCallback<SimpleResult> callback) {

        Call<SimpleResult> call = getAPIBuilder().addNewBD13(bd13Create);
        call.enqueue(callback);
    }

    public static void loginAuthorized(String mobileNumber, String signCode, CommonCallback<LoginResult> callback) {
        String signature = Utils.SHA256(mobileNumber + signCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<LoginResult> call = getAPIBuilder().loginAuthorized(mobileNumber, signCode, signature);
        call.enqueue(callback);
    }

    public static void searchDeliveryStatistic(String fromDate, String toDate, String status,
                                               String postmanId, String routeCode, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryStatistic(fromDate, toDate, status, postmanId, routeCode);
        call.enqueue(callback);
    }

    public static void getHistoryDelivery(String parcelCode, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().getHistoryDelivery(parcelCode);
        call.enqueue(callback);
    }

    public static void activeAuthorized(String mobileNumber, String activeCode, String codeDeviceActive, CommonCallback<ActiveResult> callback) {
        String signature = Utils.SHA256(mobileNumber + activeCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<ActiveResult> call = getAPIBuilder().activeAuthorized(mobileNumber, activeCode, codeDeviceActive, signature);
        call.enqueue(callback);
    }

    public static void callForwardCallCenter(String callerNumber, String calleeNumber,
                                             String callForwardType, String hotlineNumber,
                                             String ladingCode, CommonCallback<SimpleResult> callback) {
        String type = "1";
        if (Constants.HEADER_NUMBER.equals("tel:159")) {
            type = "1";
        } else {
            type = "2";
        }
        String signature = Utils.SHA256(callerNumber + calleeNumber + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().callForwardCallCenter(callerNumber, calleeNumber, callForwardType,
                hotlineNumber, ladingCode, type, signature);
        call.enqueue(callback);
    }

    public static void searchOrderPostmanCollect(String orderPostmanID,
                                                 String orderID,
                                                 String postmanID,
                                                 String status,
                                                 String fromAssignDate,
                                                 String toAssignDate, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate);
        call.enqueue(callback);
    }

    public static void searchAllOrderPostmanCollect(String orderPostmanID,
                                                    String orderID,
                                                    String postmanID,
                                                    String status,
                                                    String fromAssignDate,
                                                    String toAssignDate, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchAllOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate);
        call.enqueue(callback);
    }

    public static void searchDeliveryPostman(String postmanID,
                                             String fromDate,
                                             String toDate,
                                             String routeCode,
                                             Integer searchTpe,
                                             CommonCallback<DeliveryPostmanResponse> callback) {
        Call<DeliveryPostmanResponse> call = getAPIBuilder().searchDeliveryPostman(postmanID, fromDate, toDate, routeCode, searchTpe);
        call.enqueue(callback);
    }

    public static void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback) {
        String signature = Utils.SHA256(parcelCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<CommonObjectResult> call = getAPIBuilder().searchParcelCodeDelivery(parcelCode.toUpperCase(), signature);
        call.enqueue(callback);
    }

    public static void getInquiryAmount(String parcelCode, CommonCallback<InquiryAmountResult> callback) {
        String signature = Utils.SHA256(parcelCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<InquiryAmountResult> call = getAPIBuilder().getInquiryAmount(parcelCode.toUpperCase(), signature);
        call.enqueue(callback);
    }

    public static Observable<CommonObjectResult> findLocation(String ladingCode) {
        String signature = Utils.SHA256(ladingCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase();
        return getAPIRxBuilder().findLocation(ladingCode.toUpperCase(), signature);
    }

    public static void validationAuthorized(String mobileNumber, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().validationAuthorized(mobileNumber, signature);
        call.enqueue(callback);
    }

    public static void checkLadingCode(String parcelCode, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(parcelCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().checkLadingCode(parcelCode, signature);
        call.enqueue(callback);
    }

    public static void addNewCallCenter(String amndEmp,
                                        String postmanID,
                                        String senderPhone,
                                        String receiverPhone,
                                        String callTime,
                                        String createdDate, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(amndEmp + postmanID + senderPhone + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().addNewCallCenter(amndEmp, postmanID, senderPhone, receiverPhone, callTime, createdDate, signature);
        call.enqueue(callback);
    }

    public static void searchCallCenter(String postmanID,
                                        String fromDate,
                                        String toDate, CommonCallback<HistoryCallResult> callback) {
        String signature = Utils.SHA256(postmanID + fromDate + toDate + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<HistoryCallResult> call = getAPIBuilder().searchCallCenter(postmanID, fromDate, toDate, signature);
        call.enqueue(callback);
    }

    public static void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().paymentDelivery(request);
        call.enqueue(callback);
    }

    public static void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().paymentPaypost(request);
        call.enqueue(callback);
    }

    public static void getPostOfficeByCode(String code, String postmanID, CommonCallback<PostOfficeResult> callback) {
        Call<PostOfficeResult> call = getAPIBuilder().getPostOfficeByCode(code, postmanID);
        call.enqueue(callback);
    }

    public static void deliveryGetPaypostError(String fromDate, String toDate, CommonCallback<GachNoResult> callback) {
        Call<GachNoResult> call = getAPIBuilder().deliveryGetPaypostError(fromDate, toDate);
        call.enqueue(callback);
    }

    public static void getPostmanShift(String poCode, CommonCallback<ShiftResult> callback) {
        Call<ShiftResult> call = getAPIBuilder().getPostmanShift(poCode);
        call.enqueue(callback);
    }

    public static void getSolutionByReasonCode(String reasonCode, CommonCallback<SolutionResult> callback) {
        Call<SolutionResult> call = getAPIBuilder().getSolutionByReasonCode(reasonCode);
        call.enqueue(callback);
    }

    public static void getSolutions(CommonCallback<SolutionResult> callback) {
        Call<SolutionResult> call = getAPIBuilder().getSolutions();
        call.enqueue(callback);
    }

    public static void getReasons(CommonCallback<ReasonResult> callback) {
        Call<ReasonResult> call = getAPIBuilder().getReasons();
        call.enqueue(callback);
    }

    public static void confirmOrderPostmanCollect(String orderPostmanID, String employeeID,
                                                  String statusCode, String confirmReason, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().confirmOrderPostmanCollect(orderPostmanID, employeeID, statusCode, confirmReason);
        call.enqueue(callback);
    }


    public static void locationAddNew(String postmanID, String latitude, String longitude, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(postmanID + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().locationAddNew(postmanID, latitude, longitude,
                signature);
        call.enqueue(callback);
    }


    public static void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift, CommonCallback<HistoryCreateBd13Result> commonCallback) {
        Call<HistoryCreateBd13Result> call = getAPIBuilder().searchCreateBd13(deliveryPOCode, routePOCode, bagNumber, chuyenThu, createDate, shift);
        call.enqueue(commonCallback);
    }

    public static void updateMobile(String code, String mobileNumber, CommonCallback<SimpleResult> commonCallback) {
        Call<SimpleResult> call = getAPIBuilder().updateMobile(code, mobileNumber);
        call.enqueue(commonCallback);
    }

    public static void postImage(String filePath, CommonCallback<UploadResult> callback) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", "file_avatar.jpg", reqFile);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        Call<UploadResult> call = getAPIBuilder().postImage(body);
        call.enqueue(callback);
    }

    public static void postImageSingle(String filePath, CommonCallback<UploadSingleResult> callback) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", "file_avatar.jpg", reqFile);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        Call<UploadSingleResult> call = getAPIBuilder().postImageSingle(body);
        call.enqueue(callback);
    }

    //Thu ho BTXH

    public static void getBankAccountNumber(BankAccountNumberRequest bankAccountNumberRequest, CommonCallback<BankAccountNumberResponse> callback) {
        Call<BankAccountNumberResponse> call = getChiHoBtxhAPI().getBankAccountNumber(bankAccountNumberRequest);
        call.enqueue(callback);
    }

    public static void seaBankInquiry(SeaBankInquiryRequest seaBankInquiryRequest, CommonCallback<SeaBankInquiryResponse> callback) {
        Call<SeaBankInquiryResponse> call = getChiHoBtxhAPI().seaBankInquiry(seaBankInquiryRequest);
        call.enqueue(callback);
    }


    public static void getIdentifyCation(@NotNull CommonCallback<IdentifyCationResponse> callback) {
        Call<IdentifyCationResponse> call = getChiHoBtxhAPI().getIdentifyCation();
        call.enqueue(callback);
    }

    public static void seaBankPayment(@NotNull SeaBankPaymentRequest seaBankPaymentRequest, @NotNull CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getChiHoBtxhAPI().seaBankPayment(seaBankPaymentRequest);
        call.enqueue(callback);
    }

    public static void getHistoryPaymentSeaBank(@Nullable String mobileNumber, @Nullable String fromDate, @Nullable String toDate, @NotNull CommonCallback<SeaBankHistoryPaymentResponse> callback) {
        Call<SeaBankHistoryPaymentResponse> call = getChiHoBtxhAPI().getHistoryPaymentSeaBank(mobileNumber, fromDate, toDate);
        call.enqueue(callback);
    }

    public static void getReasonsHoanTat(CommonCallback<ReasonResult> callback) {
        Call<ReasonResult> call = getAPIBuilder().getReasonsHoanTat();
        call.enqueue(callback);
    }

    public static void getReasonsHoanTatMiss(CommonCallback<ReasonResult> callback) {
        Call<ReasonResult> call = getAPIBuilder().getReasonsHoanTatMiss();
        call.enqueue(callback);
    }

    public static void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<ConfirmAllOrderPostmanResult> callback) {
        Call<ConfirmAllOrderPostmanResult> call = getAPIBuilder().confirmAllOrderPostman(request);
        call.enqueue(callback);
    }

    public static void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().collectOrderPostmanCollect(hoanTatTinRequest);
        call.enqueue(callback);
    }

    public static void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().pushToPNSDelivery(request);
        call.enqueue(callback);
    }

    public static void searchStatisticCollect(String postmanID, String fromDate, String toDate, CommonCallback<StatisticCollectResult> callback) {
        Call<StatisticCollectResult> call = getAPIBuilder().searchStatisticCollect(postmanID, fromDate, toDate);
        call.enqueue(callback);
    }

    public static void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().collectAllOrderPostman(list);
        call.enqueue(callback);
    }

    public static void statisticDeliveryGeneral(String postmanID, String fromDate, String toDate, boolean isSuccess, String routeCode, CommonCallback<StatisticDeliveryGeneralResult> callback) {
        Call<StatisticDeliveryGeneralResult> call = getAPIBuilder().statisticDeliveryGeneral(postmanID, fromDate, toDate, isSuccess, routeCode);
        call.enqueue(callback);
    }

    public static void statisticDeliveryDetail(String serviceCode, int typeDelivery, String postmanID, String fromDate, String toDate, boolean isSuccess, String routeCode, CommonCallback<StatisticDeliveryDetailResult> callback) {
        Call<StatisticDeliveryDetailResult> call = getAPIBuilder().statisticDeliveryDetail(serviceCode, typeDelivery, postmanID, fromDate, toDate, isSuccess, routeCode);
        call.enqueue(callback);
    }

    public static void statisticDebitGeneral(String postmanID, String fromDate, String toDate, String routeCode, CommonCallback<StatisticDebitGeneralResult> callback) {
        Call<StatisticDebitGeneralResult> call = getAPIBuilder().statisticDebitGeneral(postmanID, fromDate, toDate, routeCode);
        call.enqueue(callback);
    }

    public static void statisticDebitDetail(String postmanID, String fromDate, String toDate, String statusCode, String routeCode, CommonCallback<StatisticDebitDetailResult> callback) {
        Call<StatisticDebitDetailResult> call = getAPIBuilder().statisticDebitDetail(postmanID, fromDate, toDate, statusCode, routeCode);
        call.enqueue(callback);
    }

    public static void getHomeData(String postmanCode, String routeCode, CommonCallback<HomeCollectInfoResult> callback) {
        Call<HomeCollectInfoResult> call = getAPIBuilder().getHomeData("", "", postmanCode, routeCode);
        call.enqueue(callback);
    }

    public static void searchLadingCreatedBd13(DingDongGetLadingCreateBD13Request objRequest, CommonCallback<DeliveryPostmanResponse> callback) {
        Call<DeliveryPostmanResponse> call = getAPIBuilder().searchLadingCreatedBd13(objRequest);
        call.enqueue(callback);
    }

    public static void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode, CommonCallback<DingDongGetCancelDeliveryResponse> callback) {
        Call<DingDongGetCancelDeliveryResponse> call = getAPIBuilder().getCancelDelivery(postmanCode, routeCode, ladingCode, fromDate, toDate);
        call.enqueue(callback);
    }

    public static void cancelDelivery(DingDongCancelDeliveryRequest request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().cancelDelivery(request);
        call.enqueue(callback);
    }

    public static void getDeliveryRoute(String poCode, CommonCallback<RouteInfoResult> callback) {
        Call<RouteInfoResult> call = getAPIBuilder().getDeliveryRoute(poCode);
        call.enqueue(callback);
    }

    public static void getPostmanByRoute(String poCode, int routeId, String routeType, CommonCallback<UserInfoResult> callback) {
        Call<UserInfoResult> call = getAPIBuilder().getPostmanByRoute(poCode, routeId, routeType);
        call.enqueue(callback);
    }

    public static void cancelDivided(List<DingDongCancelDividedRequest> request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().cancelDivided(request);
        call.enqueue(callback);
    }

    public static void getAddressByLocation(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback) {
        Call<XacMinhDiaChiResult> call = getAPIBuilder().getAddressByLocation(longitude, latitude);
        call.enqueue(callback);
    }

    public static void vietmapVerify(String id, String userId, String layer, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().vietmapVerify(id, userId, true, layer);
        call.enqueue(callback);
    }

    public static void vietmapUpdate(UpdateRequest request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().vietmapUpdate(request);
        call.enqueue(callback);
    }

    public static void vietmapSearch(String text, CommonCallback<XacMinhDiaChiResult> callback) {
        Call<XacMinhDiaChiResult> call = getAPIBuilder().vietmapSearch(text);
        call.enqueue(callback);
    }

    public static void vietmapRoute(List<RouteRequest> request, CommonCallback<XacMinhDiaChiResult> callback) {
        Call<XacMinhDiaChiResult> call = getAPIBuilder().vietmapRoute(request);
        call.enqueue(callback);
    }

    public static void getLadingStatusGeneral(String postmanID, String fromDate, String toDate,
                                              int ladingType, String routeCode,
                                              CommonCallback<StatisticDeliveryGeneralResult> callback) {
        Call<StatisticDeliveryGeneralResult> call = getAPIBuilder().getLadingStatusGeneral(postmanID,
                fromDate, toDate, ladingType, routeCode);
        call.enqueue(callback);
    }

    public static void getLadingStatusDetail(int type, String serviceCode, String postmanID,
                                             String fromDate, String toDate, int ladingType,
                                             String routeCode, CommonCallback<StatisticDeliveryDetailResult> callback) {
        Call<StatisticDeliveryDetailResult> call = getAPIBuilder().getLadingStatusDetail(type,
                serviceCode, postmanID, fromDate, toDate, ladingType, routeCode);
        call.enqueue(callback);
    }

    public static void statisticPayment(String postmanId, String poCode, String phoneNumber,
                                        String fromDate, String toDate,
                                        CommonCallback<StatisticPaymentResult> callback) {
        Call<StatisticPaymentResult> call = getAPIBuilder().statisticPayment(postmanId, poCode, phoneNumber, fromDate, toDate);
        call.enqueue(callback);
    }

    public static void searchForApproved(String ladingCode,
                                         String fromDate,
                                         String toDate,
                                         String postmanId,
                                         String routeId,
                                         String poCode, CommonCallback<RouteResult> callback) {
        Call<RouteResult> call = getAPIBuilder().searchForApproved(ladingCode, fromDate, toDate, postmanId, routeId, poCode);
        call.enqueue(callback);
    }

    public static void searchForCancel(String ladingCode,
                                       String fromDate,
                                       String toDate,
                                       String postmanId,
                                       String routeId,
                                       String poCode, CommonCallback<RouteResult> callback) {
        Call<RouteResult> call = getAPIBuilder().searchForCancel(ladingCode, fromDate, toDate, postmanId, routeId, poCode);
        call.enqueue(callback);
    }

    public static void approvedAgree(String id,
                                     String ladingCode,
                                     String postmanId,
                                     String postmanCode,
                                     String poCode,
                                     String routeId,
                                     String routeCode,
                                     CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().approvedAgree(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode);
        call.enqueue(callback);
    }

    public static void approvedDisagree(String id,
                                        String ladingCode,
                                        String postmanId,
                                        String postmanCode,
                                        String poCode,
                                        String routeId,
                                        String routeCode,
                                        CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().approvedDisagree(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode);
        call.enqueue(callback);
    }

    public static void cancelRoute(Integer id,
                                   Integer postmanId,
                                   CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().cancelRoute(id, postmanId);
        call.enqueue(callback);
    }


    public static void changeRouteInsert(ChangeRouteRequest request,
                                         CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().changeRouteInsert(request);
        call.enqueue(callback);
    }

    public static void getRouteLadingDetail(String ladingCode,
                                         CommonCallback<ChangeRouteResult> callback) {
        Call<ChangeRouteResult> call = getAPIBuilder().getDetailByLadingCode(ladingCode);
        call.enqueue(callback);
    }

    public static Observable<CancelDeliveryResult> cancelDeliveryStatistic(@Body CancelDeliveryStatisticRequest request) {
        return getAPIRxBuilder().cancelDeliveryStatistic(request);
    }

}

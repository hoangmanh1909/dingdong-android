package com.ems.dingdong.network;


import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.CancelDeliveryResult;
import com.ems.dingdong.model.ChangeRouteResult;
import com.ems.dingdong.model.ComfrimCreateMode;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.CreateOrderRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.DeliverySuccessRequest;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.HistoryCallResult;
import com.ems.dingdong.model.HistoryCreateBd13Result;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.LinkEWalletResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.request.ApprovedAgreeRequest;
import com.ems.dingdong.model.request.AuthGetVersionRequest;
import com.ems.dingdong.model.request.BaseRequest;
import com.ems.dingdong.model.request.CallForwardCallCenterRequest;
import com.ems.dingdong.model.request.GetCancelDeliveryRequest;
import com.ems.dingdong.model.request.GetPostOfficeByCodeRequest;
import com.ems.dingdong.model.request.GetSolutionByReasonCodeRequest;
import com.ems.dingdong.model.request.HistoryDeliveryRequest;
import com.ems.dingdong.model.request.LocationAddNewRequest;
import com.ems.dingdong.model.request.PUGetBusinessProfileRequest;
import com.ems.dingdong.model.request.RequestObject;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SearchMode;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.TokenMoveCropResult;
import com.ems.dingdong.model.UploadResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.ActiveRequest;
import com.ems.dingdong.model.request.BalanceRequest;
import com.ems.dingdong.model.request.BankAccountNumberRequest;
import com.ems.dingdong.model.request.BankLinkRequest;
import com.ems.dingdong.model.request.CallHistoryRequest;
import com.ems.dingdong.model.request.CallOTP;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.ConfirmOrderPostmanCollectRequest;
import com.ems.dingdong.model.request.DanhSachTaiKhoanRequest;
import com.ems.dingdong.model.request.DebitDetailRequest;
import com.ems.dingdong.model.request.DebitGeneralRequest;
import com.ems.dingdong.model.request.DeliveryDetailRequest;
import com.ems.dingdong.model.request.DeliveryGeneralRequest;
import com.ems.dingdong.model.request.DeliveryPaymentV2;
import com.ems.dingdong.model.request.DeliveryPostmanRequest;
import com.ems.dingdong.model.request.DeliveryProductRequest;
import com.ems.dingdong.model.request.DeliveryRouteRequest;
import com.ems.dingdong.model.request.DeliveryUnSuccessRequest;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.request.GetAddressByLocationRequest;
import com.ems.dingdong.model.request.GetDataPaymentRequest;
import com.ems.dingdong.model.request.GetPostmanByRouteRequest;
import com.ems.dingdong.model.request.GetRouteRequest;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.model.request.LadingStatusDetailRequest;
import com.ems.dingdong.model.request.LadingStatusGeneralRequest;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.request.MainViewRequest;
import com.ems.dingdong.model.request.OrderChangeRouteRequest;
import com.ems.dingdong.model.request.OrderChangeRouteDingDongManagementRequest;
import com.ems.dingdong.model.request.OrderChangeRouteInsertRequest;
import com.ems.dingdong.model.request.PayLinkConfirm;
import com.ems.dingdong.model.request.PayLinkRequest;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.request.SMLRequest;
import com.ems.dingdong.model.request.STTTicketNotifyUpdateSeenRequest;
import com.ems.dingdong.model.request.SeaBankInquiryRequest;
import com.ems.dingdong.model.request.SeaBankPaymentRequest;
import com.ems.dingdong.model.request.SearchCallCenterRequest;
import com.ems.dingdong.model.request.SearchCreateBd13Request;
import com.ems.dingdong.model.request.SearchDeliveryStatisticRequest;
import com.ems.dingdong.model.request.SearchForApprovedRequest;
import com.ems.dingdong.model.request.SearchForCancelRequest;
import com.ems.dingdong.model.request.SearchOrderPostmanCollectRequest;
import com.ems.dingdong.model.request.StatisticCollectRequest;
import com.ems.dingdong.model.request.StatisticPaymentRequest;
import com.ems.dingdong.model.request.StatisticSMLDeliveryFailRequest;
import com.ems.dingdong.model.request.TaiKhoanMatDinh;
import com.ems.dingdong.model.request.TicketNotifyRequest;
import com.ems.dingdong.model.request.TrackTraceLadingRequest;
import com.ems.dingdong.model.request.UpdateMobileRequest;
import com.ems.dingdong.model.request.ValidationRequest;
import com.ems.dingdong.model.request.VietMapSearchDecodeRequest;
import com.ems.dingdong.model.request.VietMapVerifyRequest;
import com.ems.dingdong.model.request.VietmapSearchRequest;
import com.ems.dingdong.model.request.VmRouteV2Request;
import com.ems.dingdong.model.request.vietmap.TravelSales;
import com.ems.dingdong.model.request.vietmap.UpdateRequest;
import com.ems.dingdong.model.response.BankAccountNumberResponse;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;
import com.ems.dingdong.model.response.IdentifyCationResponse;
import com.ems.dingdong.model.response.SeaBankHistoryPaymentResponse;
import com.ems.dingdong.model.response.SeaBankInquiryResponse;
import com.ems.dingdong.model.thauchi.SmartBankConfirmCancelLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankConfirmLinkRequest;
import com.ems.dingdong.model.thauchi.SmartBankInquiryBalanceRequest;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.model.thauchi.YeuCauLienKetRequest;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Log;
import com.ems.dingdong.utiles.RSAUtil;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
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
    private static volatile VinattiAPI apiBuilderVer2;
    private static volatile VinattiAPI apiRxBuilder;
    private static volatile VinattiAPI apiRxBuilderVer2;
    private static volatile VinattiAPI apiHistoryCallBuilder;
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
    private static VinattiAPI getApiBuilderVer2(){
        if (apiBuilderVer2 == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(120, 120,"RGluZ0Rvbmc6R2F0ZXdheUAyMTIyIUAj"))
                    .build();
            apiBuilderVer2 = retrofit.create(VinattiAPI.class);
        }
        return apiBuilderVer2;
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
    private static VinattiAPI getApiRxBuilderVer2() {
        if (apiRxBuilderVer2 == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(120, 120,"RGluZ0Rvbmc6R2F0ZXdheUAyMTIyIUAj"))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiRxBuilderVer2 = retrofit.create(VinattiAPI.class);
        }
        return apiRxBuilderVer2;
    }

    private static VinattiAPI getAPIRxBuilderMap() {
        if (apiRxBuilder == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(120, 120))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiRxBuilder = retrofit.create(VinattiAPI.class);
        }
        return apiRxBuilder;
    }

    private static VinattiAPI getAPIHistoryCallBuilder() {
        if (apiHistoryCallBuilder == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api-development.movecrop.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient("3e058ce3027e6c473a6d47e5f253c480:7409f5b12daff2f0b8df56f6b4faf151"))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiHistoryCallBuilder = retrofit.create(VinattiAPI.class);
        }
        return apiHistoryCallBuilder;
    }

    public static void taskOfWork(SimpleResult taskRequest, CommonCallback<SimpleResult> callback) {

//        Call<SimpleResult> call = getAPIBuilder().taskOfWork(taskRequest);
//        call.enqueue(callback);
    }

    public static void addNewBD13(Bd13Create bd13Create, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(bd13Create);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CREATE_BD13, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void loginAuthorized(LoginRequest loginRequest, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(loginRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.AUTH_LOGIN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void searchDeliveryStatistic(String fromDate, String toDate, String status,
                                               String postmanId, String routeCode, CommonCallback<SimpleResult> callback) {
        SearchDeliveryStatisticRequest request = new SearchDeliveryStatisticRequest(fromDate, toDate, status, postmanId, routeCode);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_DELIVERY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
//        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryStatistic(fromDate, toDate, status, postmanId, routeCode);
//        call.enqueue(callback);
    }

    public static void getHistoryDelivery(String parcelCode, CommonCallback<SimpleResult> callback) {
        HistoryDeliveryRequest request = new HistoryDeliveryRequest(parcelCode);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_LADDING_JOURNEY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void activeAuthorized(String mobileNumber, String activeCode, String codeDeviceActive, CommonCallback<SimpleResult> callback) {
        ActiveRequest activeRequest = new ActiveRequest(mobileNumber,activeCode,codeDeviceActive,Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase());
        String data= getGson().toJson(activeRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.AUTH_ACTIVE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static Call<SimpleResult> callForwardCallCenter(String callerNumber, String calleeNumber,
                                                           String callForwardType, String hotlineNumber,
                                                           String ladingCode, CommonCallback<SimpleResult> callback) {
        String type = "1";
        Log.d("thasndahsd12123123", Constants.HEADER_NUMBER);
        if (Constants.HEADER_NUMBER.equals("tel:159")) {
            type = "1";
        } else {
            type = "2";
        }

//        Log.d("thasdasdasdas",Constants.HEADER_NUMBER);

        CallForwardCallCenterRequest request = new CallForwardCallCenterRequest(callerNumber, calleeNumber, callForwardType,
                hotlineNumber, ladingCode, type, Utils.SHA256(callerNumber + calleeNumber + BuildConfig.PRIVATE_KEY).toUpperCase());
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CALL_CENTER_FORWARD, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
        return call;
    }

    //    public static Single<SimpleResult> searchOrderPostmanCollect(String orderPostmanID,
//                                                                 String orderID,
//                                                                 String postmanID,
//                                                                 String status,
//                                                                 String fromAssignDate,
//                                                                 String toAssignDate) {
//
////        Call<CommonObjectListResult> call = getAPIBuilder().searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate);
////        call.enqueue(callback);
//        return null;
//    }
    public static void searchOrderPostmanCollect(String orderPostmanID,
                                                 String orderID,
                                                 String postmanID,
                                                 String status,
                                                 String fromAssignDate,
                                                 String toAssignDate, CommonCallback<SimpleResult> callback) {
        SearchOrderPostmanCollectRequest searchOrderPostmanCollectRequest = new SearchOrderPostmanCollectRequest(orderPostmanID,orderID,postmanID,status,fromAssignDate,toAssignDate);
        String data= getGson().toJson(searchOrderPostmanCollectRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_SEARCH_ORDER_POSTMAN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void searchAllOrderPostmanCollect(String orderPostmanID,
                                                    String orderID,
                                                    String postmanID,
                                                    String status,
                                                    String fromAssignDate,
                                                    String toAssignDate, CommonCallback<SimpleResult> callback) {

        SearchOrderPostmanCollectRequest searchOrderPostmanCollectRequest = new SearchOrderPostmanCollectRequest(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate);
        String data= getGson().toJson(searchOrderPostmanCollectRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_SEARCH_ORDER_POSTMAN_COLLECT_ALL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

//    public static Call<DeliveryPostmanResponse> searchDeliveryPostman(String postmanID,
//                                                                      String fromDate,
//                                                                      String toDate,
//                                                                      String routeCode,
//                                                                      Integer searchTpe,
//                                                                      CommonCallback<DeliveryPostmanResponse> callback) {
//        Call<DeliveryPostmanResponse> call = getAPIBuilder().searchDeliveryPostman(postmanID, fromDate, toDate, routeCode, searchTpe);
//        call.enqueue(callback);
//        return call;
//    }
    public static Call<SimpleResult> searchDeliveryPostman(String postmanID,
                                             String fromDate,
                                             String toDate,
                                             String routeCode,
                                             Integer searchTpe,
                                             CommonCallback<SimpleResult> callback) {

        DeliveryPostmanRequest deliveryPostmanRequest = new DeliveryPostmanRequest(postmanID,fromDate,toDate,routeCode,searchTpe);
        String data= getGson().toJson(deliveryPostmanRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DELIVERY_POSTMAN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
        return call;
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

//    public static Observable<CommonObjectResult> findLocation(String ladingCode, String poCode) {
//        String signature = Utils.SHA256(ladingCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase();
//        return getAPIRxBuilder().findLocation(ladingCode.toUpperCase(), poCode, signature);
//    }
    public static Observable<CommonObjectResult> findLocation(String ladingCode, String poCode){
        TrackTraceLadingRequest trackTraceLadingRequest = new TrackTraceLadingRequest(ladingCode,poCode,Utils.SHA256(ladingCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase());
        String data= getGson().toJson(trackTraceLadingRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_TRACK_TRACE_LADING, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().findLocation(requestObject);

    }

    public static void validationAuthorized(String mobileNumber, CommonCallback<SimpleResult> callback) {
        ValidationRequest validationRequest = new ValidationRequest(mobileNumber,Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase());
        String data= getGson().toJson(validationRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.AUTH_VALIDATION, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
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
                                        String toDate, CommonCallback<SimpleResult> callback) {
        SearchCallCenterRequest request = new SearchCallCenterRequest(postmanID, fromDate, toDate, Utils.SHA256(postmanID + fromDate + toDate + BuildConfig.PRIVATE_KEY).toUpperCase());
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CALL_CENTER_SEARCH, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void paymentDelivery(PaymentDeviveryRequest request, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_PAYMENT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static Single<SimpleResult> paymentDelivery(PaymentDeviveryRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_PAYMENT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static void paymentPaypost(PaymentPaypostRequest request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().paymentPaypost(request);
        call.enqueue(callback);
    }

    public static void getPostOfficeByCode(String code, String postmanID, CommonCallback<SimpleResult> callback) {
        GetPostOfficeByCodeRequest request = new GetPostOfficeByCodeRequest(code,postmanID);
//        Call<PostOfficeResult> call = getAPIBuilder().getPostOfficeByCode(code, postmanID);
//        call.enqueue(callback);

        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.AUTH_GET_POST_OFFICE_BY_CODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void deliveryGetPaypostError(String fromDate, String toDate, CommonCallback<GachNoResult> callback) {
        Call<GachNoResult> call = getAPIBuilder().deliveryGetPaypostError(fromDate, toDate);
        call.enqueue(callback);
    }

    public static void getPostmanShift(String poCode, CommonCallback<SimpleResult> callback) {
        BaseRequest baseRequest = new BaseRequest(null,null,null,poCode);
        String data= getGson().toJson(baseRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DIC_GET_POSTMAN_SHIFT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getSolutionByReasonCode(String reasonCode, CommonCallback<SimpleResult> callback) {
        GetSolutionByReasonCodeRequest reasonCodeRequest = new GetSolutionByReasonCodeRequest(reasonCode);
        String data= getGson().toJson(reasonCodeRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DLV_GET_SOLUTIONS_BY_REASON, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getSolutions(CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson("");
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_GET_SOLUTIONS, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getReasons(CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson("");
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DELIVERY_REASONS, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }
    public static void getBalance(String mobileNumber,String postmanId,CommonCallback<SimpleResult> callback) {
        BalanceRequest validationRequest = new BalanceRequest(mobileNumber,postmanId);
        String data= getGson().toJson(validationRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_POSTMAN_GET_BALANCE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getVersion(CommonCallback<SimpleResult> callback) {
        AuthGetVersionRequest request = new AuthGetVersionRequest("DD_ANDROID");
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.AUTH_GET_VERSION, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static Single<SimpleResult> getSmartBankLink(String userName,String unitCode ){
        BankLinkRequest bankLinkRequest = new BankLinkRequest(userName,unitCode);
        String data= getGson().toJson(bankLinkRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.AUTH_GET_BANK_LINK, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);

    }

    public static void createCallHistoryVHT(String code, String data, String signature, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().Bussiness(code, data, signature);
        call.enqueue(callback);
    }

    public static void confirmOrderPostmanCollect(String orderPostmanID, String employeeID,
                                                  String statusCode, String confirmReason, CommonCallback<SimpleResult> callback) {
        ConfirmOrderPostmanCollectRequest collectRequest = new ConfirmOrderPostmanCollectRequest(orderPostmanID, employeeID, statusCode, confirmReason,"DD_ANDROID");
        String data= getGson().toJson(collectRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_CONFIRM_ORDER_POSTMAN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }


    public static void locationAddNew(String postmanID, String latitude, String longitude, CommonCallback<SimpleResult> callback) {
//        String signature = Utils.SHA256(postmanID + BuildConfig.PRIVATE_KEY).toUpperCase();
//        Call<SimpleResult> call = getAPIBuilder().locationAddNew(postmanID, latitude, longitude,
//                signature);
//        call.enqueue(callback);
        LocationAddNewRequest request = new  LocationAddNewRequest(postmanID,latitude,longitude,Utils.SHA256(postmanID + BuildConfig.PRIVATE_KEY).toUpperCase());
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_LOCATION_ADD_NEW, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }


    public static void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift, CommonCallback<SimpleResult> commonCallback) {
        SearchCreateBd13Request searchCreateBd13Request = new SearchCreateBd13Request(deliveryPOCode, routePOCode, bagNumber, chuyenThu, createDate, shift);
        String data= getGson().toJson(searchCreateBd13Request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CREATE_SEARCH_BD13, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(commonCallback);
    }

    public static Call<SimpleResult> updateMobile(String code, String type, String mobileNumber, CommonCallback<SimpleResult> commonCallback) {
        UpdateMobileRequest  updateMobileRequest = new UpdateMobileRequest(code, type, mobileNumber);
        String data= getGson().toJson(updateMobileRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_UPDATE_MOBILE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(commonCallback);
        return call;
    }

    public static void postImage(String filePath, CommonCallback<UploadResult> callback) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", "file_avatar.jpg", reqFile);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        Call<UploadResult> call = getApiBuilderVer2().postImage(body);
        call.enqueue(callback);
    }

    public static void postImageSingle(String filePath, CommonCallback<UploadSingleResult> callback) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", "file_avatar.jpg", reqFile);
        ///MultipartBody.Part bodyAvatar = MultipartBody.Part.createFormData("avatar", "file_selfie_avatar.jpg", reqFile);///
        //MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        Call<UploadSingleResult> call = getApiBuilderVer2().postImageSingle(body);
        ///Call<UploadSingleResult> callAvatar = getAPIBuilder().postImageSingle(bodyAvatar);///
        call.enqueue(callback);
        ///callAvatar.enqueue(callback);///
    }

    ///
    public static void postImageAvatar(String filePath, CommonCallback<UploadSingleResult> callback) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part bodyAvatar = MultipartBody.Part.createFormData("image_avatar", "file_selfie_avatar.jpg", reqFile);
        Call<UploadSingleResult> call = getApiBuilderVer2().postImageSingle(bodyAvatar);
        call.enqueue(callback);
    }

    public static Observable<UploadSingleResult> postImageObservable(String filePath) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", "file_avatar.jpg", reqFile);
        //MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        return getApiRxBuilderVer2().postImageObservable(body);
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

    public static void getReasonsHoanTat(CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson("");
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_DIC_GET_PICKUP_REASON, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getReasonsHoanTatMiss(CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson("");
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_GET_CANCEL_ORDER_REASON, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void confirmAllOrderPostman(ArrayList<ConfirmOrderPostman> request, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_CONFIRM_ALL_ORDER_POSTMAN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void collectOrderPostmanCollect(HoanTatTinRequest hoanTatTinRequest, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(hoanTatTinRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_COLLECT_ORDER_POSTMAN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void pushToPNSDelivery(PushToPnsRequest request, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().pushToPNSDelivery(request);
        call.enqueue(callback);
    }

    public static void pushToPNSDeliveryUnSuccess(DeliveryUnSuccessRequest request, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DELIVERY_UN_SUCCESS, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static Single<SimpleResult> pushToPNSDelivery(PushToPnsRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_PUSH_TO_PNS, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static void searchStatisticCollect(String postmanID, String fromDate, String toDate, CommonCallback<SimpleResult> callback) {
        StatisticCollectRequest statisticCollectRequest = new StatisticCollectRequest(postmanID,fromDate,toDate);
        String data= getGson().toJson(statisticCollectRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_COLLECT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void collectAllOrderPostman(List<HoanTatTinRequest> list, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(list);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_COLLECT_ALL_ORDER_POSTMAN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void statisticDeliveryGeneral(String postmanID, String fromDate, String toDate, boolean isSuccess, String routeCode, CommonCallback<SimpleResult> callback) {
        DeliveryGeneralRequest deliveryGeneralRequest = new DeliveryGeneralRequest(postmanID,fromDate,toDate,isSuccess,routeCode);
        String data= getGson().toJson(deliveryGeneralRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_DELIVERY_GENERAL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void statisticDeliveryDetail(String serviceCode, int typeDelivery, String postmanID, String fromDate, String toDate, boolean isSuccess, String routeCode, CommonCallback<SimpleResult> callback) {
        DeliveryDetailRequest deliveryDetailRequest = new DeliveryDetailRequest(serviceCode,typeDelivery,postmanID,fromDate,toDate,isSuccess,routeCode);
        String data= getGson().toJson(deliveryDetailRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_DELIVERY_DETAILL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void statisticDebitGeneral(String postmanID, String fromDate, String toDate, String routeCode, CommonCallback<SimpleResult> callback) {
        DebitGeneralRequest debitGeneralRequest = new DebitGeneralRequest(postmanID,fromDate,toDate,routeCode);
        String data= getGson().toJson(debitGeneralRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_DEBIT_GENERAL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void statisticDebitDetail(String postmanID, String fromDate, String toDate, String statusCode, String routeCode, CommonCallback<SimpleResult> callback) {
        DebitDetailRequest debitDetailRequest = new DebitDetailRequest(postmanID,fromDate,toDate,statusCode,routeCode);
        String data= getGson().toJson(debitDetailRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_DEBIT_DETAILL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getHomeData(String fromDate, String toDate, String postmanCode, String routeCode, CommonCallback<HomeCollectInfoResult> callback) {
        Call<HomeCollectInfoResult> call = getAPIBuilder().getHomeData(fromDate, toDate, postmanCode, routeCode);
        call.enqueue(callback);
    }
    public static void getHomeDataCommonService(String fromData,String toDate,String postmanCode,String routeCode,String funcRequest,CommonCallback<SimpleResult> callback){
        MainViewRequest mainViewRequest = new MainViewRequest(fromData,toDate,postmanCode,routeCode);
        String data= getGson().toJson(mainViewRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", funcRequest, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void searchLadingCreatedBd13(DingDongGetLadingCreateBD13Request objRequest, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(objRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_GET_LADING_BD13, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getCancelDelivery(String postmanCode, String routeCode, String fromDate, String toDate, String ladingCode, CommonCallback<SimpleResult> callback) {
        GetCancelDeliveryRequest getCancelDeliveryRequest = new GetCancelDeliveryRequest(postmanCode, routeCode, fromDate, toDate);
        String data= getGson().toJson(getCancelDeliveryRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_GET_CANCEL_DELIVERY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void cancelDelivery(DingDongCancelDeliveryRequest request, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        android.util.Log.e("TAG", "cancelDelivery: "+data );
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CANCEL_DELIVERY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getDeliveryRoute(String poCode, CommonCallback<SimpleResult> callback) {
        DeliveryRouteRequest deliveryRouteRequest = new DeliveryRouteRequest(poCode);
        String data= getGson().toJson(deliveryRouteRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DELIVERY_ROUTE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getRoute(String poCode, CommonCallback<SimpleResult> callback) {
        GetRouteRequest getRouteRequest = new GetRouteRequest(poCode, "P");
        String data= getGson().toJson(getRouteRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DELIVERY_ROUTE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getPostmanByRoute(String poCode, int routeId, String routeType, CommonCallback<SimpleResult> callback) {
        GetPostmanByRouteRequest getRouteRequest = new GetPostmanByRouteRequest(poCode, routeId,routeType);
        String data= getGson().toJson(getRouteRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_DIC_POSTMAN_BY_ROUTE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void cancelDivided(List<DingDongCancelDividedRequest> request, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CANCEL_DIVIDED, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }


    public static void deliveryPartial(DeliveryProductRequest request, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DELIVERY_PARTIAL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getAddressByLocation(double longitude, double latitude, CommonCallback<XacMinhDiaChiResult> callback) {
        GetAddressByLocationRequest addressByLocationRequest  =  new GetAddressByLocationRequest(longitude,latitude);
        String data= getGson().toJson(addressByLocationRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_REVERSE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<XacMinhDiaChiResult> call = getApiBuilderVer2().getAddressByLocation(requestObject);
        call.enqueue(callback);
    }

    public static void vietmapVerify(String id, String userId, String layer, CommonCallback<SimpleResult> callback) {
        VietMapVerifyRequest vietMapVerifyRequest  =  new VietMapVerifyRequest(id,userId,true,layer);
        String data= getGson().toJson(vietMapVerifyRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_VERIFY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void vietmapUpdate(UpdateRequest request, CommonCallback<SimpleResult> callback) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_UPDATE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static Single<XacMinhDiaChiResult> vietmapSearch(String text, Double longitude, Double latitude) {
        VietmapSearchRequest vietmapSearchRequest = new VietmapSearchRequest(text,longitude,latitude);
        String data= getGson().toJson(vietmapSearchRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_SEARCH, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().vietmapSearch(requestObject);
    }

    public static Single<XacMinhDiaChiResult> vietmapVitri(String text) {
        return getAPIRxBuilderMap().vietmapVitri(text);
    }

    public static Single<XacMinhDiaChiResult> vietmapVitriEndCode(Double longitude, Double latitude) {
        GetAddressByLocationRequest vietmapSearchEncode  =  new GetAddressByLocationRequest(longitude,latitude);
        String data= getGson().toJson(vietmapSearchEncode);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_ENCODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().vietmapSearchEncode(requestObject);
    }

    public static Single<DecodeDiaChiResult> vietmapSearchDecode(String decode) {
        VietMapSearchDecodeRequest vietMapSearchDecodeRequest  =  new VietMapSearchDecodeRequest(decode);
        String data= getGson().toJson(vietMapSearchDecodeRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_DECODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().vietmapSearchDecode(requestObject);
    }

    public static void vietmapRoute(List<String> request, CommonCallback<XacMinhDiaChiResult> callback) {
        VmRouteV2Request vmRouteV2Request = new VmRouteV2Request(request,"");
        String data= getGson().toJson(vmRouteV2Request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_ROUTE_V2, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<XacMinhDiaChiResult> call = getApiBuilderVer2().vietmapRoute(requestObject);
        call.enqueue(callback);
    }

//    public static void vietmapTravelSalesmanProblem( List<RouteRequest> request, CommonCallback<XacMinhDiaChiResult> callback) {
//        Call<XacMinhDiaChiResult> call = getAPIBuilder().vietmapTravelSalesmanProblem(request);
//        call.enqueue(callback);
//    }


    public static void getLadingStatusGeneral(String postmanID, String fromDate, String toDate,
                                              int ladingType, String routeCode,
                                              CommonCallback<SimpleResult> callback) {
        LadingStatusGeneralRequest ladingStatusGeneralRequest = new LadingStatusGeneralRequest(postmanID,fromDate,toDate,ladingType,routeCode);
        String data= getGson().toJson(ladingStatusGeneralRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_LADING_STATUS_GENERAL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void getLadingStatusDetail(int type, String serviceCode, String postmanID,
                                             String fromDate, String toDate, int ladingType,
                                             String routeCode, CommonCallback<SimpleResult> callback) {
        LadingStatusDetailRequest ladingStatusDetailRequest = new LadingStatusDetailRequest(type,serviceCode,postmanID,fromDate,toDate,ladingType,routeCode);
        String data= getGson().toJson(ladingStatusDetailRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_LADING_STATUS_DETAIL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static void statisticPayment(String postmanId, String poCode, String phoneNumber,
                                        String fromDate, String toDate,
                                        CommonCallback<SimpleResult> callback) {
        StatisticPaymentRequest request = new StatisticPaymentRequest(postmanId, poCode, phoneNumber, fromDate, toDate);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_PAYMENT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
//        Call<StatisticPaymentResult> call = getAPIBuilder().statisticPayment(postmanId, poCode, phoneNumber, fromDate, toDate);
//        call.enqueue(callback);
    }

    public static void searchForApproved(String ladingCode,
                                         String fromDate,
                                         String toDate,
                                         String postmanId,
                                         String routeId,
                                         String poCode,
                                         String statusCode,
                                         Integer fromRouteId,
                                         CommonCallback<SimpleResult> callback) {
        SearchForApprovedRequest request = new SearchForApprovedRequest(ladingCode, fromDate, toDate, postmanId, routeId, poCode, statusCode, fromRouteId);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_OCR_SEARCH_FOR_APPROVE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);

    }

    public static void searchForCancel(String ladingCode,
                                       String fromDate,
                                       String toDate,
                                       String postmanId,
                                       String routeId,
                                       String poCode,
                                       String statusCode,
                                       Integer fromRouteId,
                                       CommonCallback<SimpleResult> callback) {
//        Call<RouteResult> call = getAPIBuilder().searchForCancel(ladingCode, fromDate, toDate, postmanId, routeId, poCode, statusCode, fromRouteId);
//        call.enqueue(callback);

        SearchForCancelRequest request = new SearchForCancelRequest(ladingCode, fromDate, toDate, postmanId, routeId, poCode, statusCode, fromRouteId);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_OCR_SEARCH_FOR_CANCEL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
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
        ApprovedAgreeRequest request = new ApprovedAgreeRequest(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CHANGE_ROUTE_APPROVE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
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

        ApprovedAgreeRequest request = new ApprovedAgreeRequest(id, ladingCode, postmanId, postmanCode, poCode, routeId, routeCode);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CHANGE_ROUTE_DISAGREE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
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
                                            CommonCallback<SimpleResult> callback) {

        BaseRequest request = new BaseRequest(null,null,ladingCode,null);
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_OCR_GET_DETAIL_BY_LADDING_CODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        Call<SimpleResult> call = getApiBuilderVer2().commonService(requestObject);
        call.enqueue(callback);
    }

    public static Observable<SimpleResult> cancelDeliveryStatistic(CancelDeliveryStatisticRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_LADING_CANCEL_DELIVERY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().cancelDeliveryStatistic(requestObject);
    }

    public static Single<TokenMoveCropResult> getAccessTokenAndroid(String mobileNumber) {
        return getAPIRxBuilder().getAccessTokenAndroid(mobileNumber);
    }

    public static Single<LinkEWalletResult> linkEWallet(PayLinkRequest payLinkRequest) {
        payLinkRequest.setSignature(Utils.SHA256(payLinkRequest.getPostmanTel()
                + payLinkRequest.getPostmanCode() + payLinkRequest.getpOCode()
                + BuildConfig.E_WALLET_SIGNATURE_KEY).toUpperCase());
        String data= getGson().toJson(payLinkRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EW_PAYLINK_REQUEST, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().linkEWallet(requestObject);
    }

    public static Single<SimpleResult> verifyLinkWithOtp(PayLinkConfirm payLinkConfirm) {
        payLinkConfirm.setSignature(Utils.SHA256(payLinkConfirm.getRequestId()
                + payLinkConfirm.getOTPCode() + payLinkConfirm.getPostmanTel()
                + payLinkConfirm.getPostmanCode() + payLinkConfirm.getpOCode()
                + BuildConfig.E_WALLET_SIGNATURE_KEY).toUpperCase());
        String data= getGson().toJson(payLinkConfirm);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EW_PAYLINK_CONFIRM, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<XacMinhDiaChiResult> vietmapTravelSalesmanProblem(TravelSales request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.VM_TRAVEL_SALESMAN_PROBLEM, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().vietmapTravelSalesmanProblemV1(requestObject);
    }

    public static Single<SimpleResult> checkAmountPayment(List<PaypostPaymentRequest> request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_CHECK_AMOUNT_PAYMENT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> checkDeliverySuccess(DeliverySuccessRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_DELIVERY_SUCCESS, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> paymentV2(DeliveryPaymentV2 request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_PAYMENT_V2, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getHistoryCall(CallHistoryRequest request) {
        return getAPIHistoryCallBuilder().getHistoryCall(
                request.getTenantID(),
                request.getCaller(),
                request.getCallee());
    }

    public static Single<SimpleResult> getDataPayment(String serviceCode, String fromDate, String toDate,
                                                           String poCode, String routeCode,
                                                           String postmanCode) {
        GetDataPaymentRequest dataPaymentRequest = new GetDataPaymentRequest(serviceCode, fromDate, toDate, poCode, routeCode, postmanCode);
        String data= getGson().toJson(dataPaymentRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EW_GET_DATA_PAYMENT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getHistoryPayment(DataRequestPayment dataRequestPayment) {
        String signature = RSAUtil.signature(dataRequestPayment.getData());
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EW_GET_DATA_HISTORY, dataRequestPayment.getData(), Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }


    public static Single<SimpleResult> saveToaDoGom(List<SenderVpostcodeMode> request) {
//        return getAPIRxBuilder().saveToaDoGom(request);

        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_SENDER_VPOST_CODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);


    }

    public static Single<SimpleResult> saveToaDoPhat(List<ReceiverVpostcodeMode> request) {
//        return getAPIRxBuilder().saveToaDoPhat(request);

        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_RECEIVER_VPOST_CODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> requestPayment(PaymentRequestModel paymentRequestModel) {
        paymentRequestModel.setSignature(Utils.SHA256(paymentRequestModel.getPostmanCode()
                + paymentRequestModel.getPoCode()
                + paymentRequestModel.getRouteCode()
                + paymentRequestModel.getPaymentToken()
                + BuildConfig.E_WALLET_SIGNATURE_KEY).toUpperCase());
        String data= getGson().toJson(paymentRequestModel);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EW_PAYMENT_REQUEST, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> deletePayment(DataRequestPayment dataRequestPayment) {
        String signature = RSAUtil.signature(dataRequestPayment.getData());
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EW_REMOVE_DATA, dataRequestPayment.getData(), Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> confirmPayment(PaymentConfirmModel paymentConfirmModel) {
        paymentConfirmModel.setSignature(Utils.SHA256(paymentConfirmModel.getPostmanCode()
                + paymentConfirmModel.getPoCode()
                + paymentConfirmModel.getRouteCode()
                + paymentConfirmModel.getTransId()
                + paymentConfirmModel.getOtpCode()
                + paymentConfirmModel.getRetRefNumber()
                + paymentConfirmModel.getPaymentToken()
                + BuildConfig.E_WALLET_SIGNATURE_KEY).toUpperCase());
        String data= getGson().toJson(paymentConfirmModel);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.EW_PAYMENT_CONFIRM, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> statisticSMLDeliveryFail(StatisticSMLDeliveryFailRequest request) {
//        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("STT001");
//        dataRequestPayment.setData(getGson().toJson(request));
//        return getAPIRxBuilder().commonService(dataRequestPayment);

        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_SML_DELIVERY_STATISTIC, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> orderChangeRoute(OrderChangeRouteInsertRequest request) {
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_ORDER_CHANGE_ROUTE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getChangeRouteOrder(OrderChangeRouteDingDongManagementRequest request) {
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_ORDER_CHANGE_ROUTE_MANAGE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }


    public static Single<SimpleResult> cancelOrder(OrderChangeRouteRequest request) {
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_ORDER_CHANGE_ROUTE_CANCEL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }


    public static Single<SimpleResult> rejectOrder(OrderChangeRouteRequest request) {
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_ORDER_CHANGE_ROUTE_REJECT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> approveOrder(OrderChangeRouteRequest request) {
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_ORDER_CHANGE_ROUTE_ACCEPT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }


    public static Single<SimpleResult> getListTicket(TicketNotifyRequest request) {
        String data = getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_GET_TICKET_NOTIFY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> isSeen(List<String> request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_GET_TICKET_NOTIFY_UPDATE_SEEN, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getDetailTicket(String ticketCode) {
        STTTicketNotifyUpdateSeenRequest seenRequest = new STTTicketNotifyUpdateSeenRequest(ticketCode);
        String data= getGson().toJson(seenRequest);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.STT_GET_TICKET_NOTIFY_BY_CODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getTinhThanhPho(BaseRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_DIC_GET_PROVINCES, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getQuanHuyen(BaseRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_DIC_GET_DISTRICTS, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getXaPhuong(BaseRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_DIC_GET_WARDS, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> getListBuuCucHuyen(String poCode) {
//        dataRequestPayment.setCode("DIC004");
        BaseRequest request = new BaseRequest(null,null,null,poCode);

        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_DIC_GET_POS_IN_DISTRICT, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> search(PUGetBusinessProfileRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_BP_GET_CHILD_BY_CODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> searchdaichi(PUGetBusinessProfileRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_BP_GET_BY_ID, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> themTin(CreateOrderRequest request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("ORD001");
        dataRequestPayment.setData(getGson().toJson(request));
        String data= getGson().toJson(dataRequestPayment);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.PU_CREATE_ORDER, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);

    }


    public static Single<SimpleResult> searchTu(String request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SML001");
        dataRequestPayment.setData(request);
//        return getAPIRxBuilder().commonService(dataRequestPayment);


        String data= getGson().toJson(dataRequestPayment);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_SML_GET_HUB_BY_POCODE, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> phatSml(SMLRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_SML_DELIVERY, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }

    public static Single<SimpleResult> huySml(SMLRequest request) {
        String data= getGson().toJson(request);
        String signature = RSAUtil.signature(data);
        RequestObject requestObject = new RequestObject("ANDROID", "", Constants.DLV_SML_DELIVERY_CANCEL, data, Utils.getLocalTime(Constants.DATE_FORMAT),"", signature);
        return getApiRxBuilderVer2().commonServiceRx(requestObject);
    }


    public static Single<SimpleResult> searchCreate(SearchMode request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("BD13001");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> comfirmCreate(ComfrimCreateMode request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("BD13002");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> getDanhSachNganHang() {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB001");
        dataRequestPayment.setData(getGson().toJson(""));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> getDanhSachTaiKhoan(DanhSachTaiKhoanRequest danhSachTaiKhoanRequest) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB007");
        dataRequestPayment.setData(getGson().toJson(danhSachTaiKhoanRequest));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> yeuCauLienKet(YeuCauLienKetRequest request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB002");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> smartBankConfirmLinkRequest(SmartBankConfirmLinkRequest request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB003");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> smartBankRequestCancelLinkRequest(SmartBankRequestCancelLinkRequest request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB004");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> SmartBankConfirmCancelLinkRequest(SmartBankConfirmCancelLinkRequest request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB005");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> ddTruyVanSodu(SmartBankInquiryBalanceRequest request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB006");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> ddCallOTP(CallOTP request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB008");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }

    public static Single<SimpleResult> ddTaiKhoanMacDinh(TaiKhoanMatDinh request) {
        DataRequestPayment dataRequestPayment = new DataRequestPayment();
//        dataRequestPayment.setCode("SMB009");
        dataRequestPayment.setData(getGson().toJson(request));
        return getAPIRxBuilder().commonService(dataRequestPayment);
    }
}

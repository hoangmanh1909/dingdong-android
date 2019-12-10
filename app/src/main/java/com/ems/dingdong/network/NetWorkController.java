package com.ems.dingdong.network;


import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.ConfirmOrderPostmanRequest;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.request.BankAccountNumberRequest;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.model.request.SeaBankInquiryRequest;
import com.ems.dingdong.model.request.SeaBankPaymentRequest;
import com.ems.dingdong.model.response.BankAccountNumberResponse;
import com.ems.dingdong.model.response.IdentifyCationResponse;
import com.ems.dingdong.model.response.SeaBankHistoryPaymentResponse;
import com.ems.dingdong.model.response.SeaBankInquiryResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.model.ActiveResult;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.HistoryCallResult;
import com.ems.dingdong.model.HistoryCreateBd13Result;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.UploadResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ems.dingdong.utiles.Utils.getUnsafeOkHttpClient;


public class NetWorkController {

    private NetWorkController() {
    }

    private static volatile VinattiAPI apiBuilder;
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

    public static void searchDeliveryStatistic(String fromDate, String status,
                                               String postmanId, String shift, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryStatistic(fromDate, status, postmanId, shift);
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

    public static void searchDeliveryPostman(String postmanID,
                                             String fromDate,
                                             String shiftID, String chuyenthu, String tuiso, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryPostman(postmanID, fromDate, shiftID, chuyenthu, tuiso);
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

    public static void findLocation(String ladingCode, CommonCallback<CommonObjectResult> callback) {
        String signature = Utils.SHA256(ladingCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<CommonObjectResult> call = getAPIBuilder().findLocation(ladingCode.toUpperCase(), signature);
        call.enqueue(callback);
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

    public static void pushToPNSDelivery(String postmanID,
                                         String ladingCode,
                                         String deliveryPOCode,
                                         String deliveryDate,
                                         String deliveryTime,
                                         String receiverName,
                                         String reasonCode,
                                         String solutionCode,
                                         String status,
                                         String paymentChannel,
                                         String deliveryType,
                                         String signatureCapture, String note, String amount, String ladingPostmanID,String routeCode, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate,
                deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType,
                signatureCapture, note, amount, ladingPostmanID, Constants.SHIFT, routeCode, signature);
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

    public static void paymentDelivery(String postmanID,
                                       String parcelCode,
                                       String mobileNumber,
                                       String deliveryPOCode,
                                       String deliveryDate,
                                       String deliveryTime,
                                       String receiverName,
                                       String receiverIDNumber,
                                       String reasonCode,
                                       String solutionCode,
                                       String status,
                                       String paymentChannel,
                                       String deliveryType,
                                       String signatureCapture,
                                       String note,
                                       String collectAmount,String routeCode,
                                       CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, collectAmount, Constants.SHIFT,routeCode, signature);
        call.enqueue(callback);
    }

    public static void paymentPaypost(String postmanID,
                                      String parcelCode,
                                      String mobileNumber,
                                      String deliveryPOCode,
                                      String deliveryDate,
                                      String deliveryTime,
                                      String receiverName,
                                      String receiverIDNumber,
                                      String reasonCode,
                                      String solutionCode,
                                      String status,
                                      String paymentChannel,
                                      String deliveryType,
                                      String signatureCapture,
                                      String note,
                                      String collectAmount,String routeCode,
                                      CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().paymentPaypost(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, collectAmount, Constants.SHIFT,routeCode, signature);
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

    public static void collectOrderPostmanCollect(String employeeID, String orderID, String orderPostmanID,
                                                  String statusCode, String quantity, String collectReason, String pickUpDate,
                                                  String pickUpTime, String file, String scan, String reasonCode, CommonCallback<SimpleResult> callback) {
            Call<SimpleResult> call = getAPIBuilder().collectOrderPostmanCollect(employeeID, orderID, orderPostmanID,
                    statusCode, quantity, collectReason, pickUpDate, pickUpTime, file, scan, reasonCode);
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
}

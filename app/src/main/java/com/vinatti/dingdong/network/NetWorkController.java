package com.vinatti.dingdong.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ActiveResult;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.utiles.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vinatti.dingdong.utiles.Utils.getUnsafeOkHttpClient;


public class NetWorkController {

    private NetWorkController() {
    }

    private static volatile VinattiAPI apiBuilder;


    public static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    private static VinattiAPI getAPIBuilder() {
        if (apiBuilder == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient(60, 60))
                    .build();
            apiBuilder = retrofit.create(VinattiAPI.class);
        }
        return apiBuilder;
    }


    public static void taskOfWork(SimpleResult taskRequest, CommonCallback<SimpleResult> callback) {

        Call<SimpleResult> call = getAPIBuilder().taskOfWork(taskRequest);
        call.enqueue(callback);
    }

    public static void loginAuthorized(String mobileNumber, String signCode, CommonCallback<LoginResult> callback) {
        String signature = Utils.SHA256(mobileNumber + signCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<LoginResult> call = getAPIBuilder().loginAuthorized(mobileNumber, signCode, signature);
        call.enqueue(callback);
    }

    public static void searchDeliveryStatistic(String fromDate, String status,
                                               String postmanId, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryStatistic(fromDate, status, postmanId);
        call.enqueue(callback);
    }

    public static void activeAuthorized(String mobileNumber, String activeCode, String codeDeviceActive, CommonCallback<ActiveResult> callback) {
        String signature = Utils.SHA256(mobileNumber + activeCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<ActiveResult> call = getAPIBuilder().activeAuthorized(mobileNumber, activeCode, codeDeviceActive, signature);
        call.enqueue(callback);
    }

    public static void callForwardCallCenter(String callerNumber, String calleeNumber,
                                             String callForwardType, String hotlineNumber,
                                             CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(callerNumber + calleeNumber + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().callForwardCallCenter(callerNumber, calleeNumber, callForwardType, hotlineNumber, signature);
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
                                             String route,
                                             String order, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryPostman(postmanID, fromDate, route, order);
        call.enqueue(callback);
    }

    public static void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback) {
        String signature = Utils.SHA256(parcelCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<CommonObjectResult> call = getAPIBuilder().searchParcelCodeDelivery(parcelCode.toUpperCase(), signature);
        call.enqueue(callback);
    }

    public static void validationAuthorized(String mobileNumber, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().validationAuthorized(mobileNumber, signature);
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
                                         String signatureCapture, String note, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate, deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType, signatureCapture, note, signature);
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
                                       CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, signature);
        call.enqueue(callback);
    }

    public static void getPostOfficeByCode(String code, CommonCallback<PostOfficeResult> callback) {
        Call<PostOfficeResult> call = getAPIBuilder().getPostOfficeByCode(code);
        call.enqueue(callback);
    }

    public static void getSolutionByReasonCode(String reasonCode, CommonCallback<SolutionResult> callback) {
        Call<SolutionResult> call = getAPIBuilder().getSolutionByReasonCode(reasonCode);
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
                                                  String pickUpTime, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().collectOrderPostmanCollect(employeeID, orderID, orderPostmanID,
                statusCode, quantity, collectReason, pickUpDate, pickUpTime);
        call.enqueue(callback);
    }


}

package com.vinatti.dingdong.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ActiveResult;
import com.vinatti.dingdong.model.Bd13Create;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.GachNoResult;
import com.vinatti.dingdong.model.HistoryCallResult;
import com.vinatti.dingdong.model.HistoryCreateBd13Result;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.model.CommonObjectListResult;
import com.vinatti.dingdong.model.UploadResult;
import com.vinatti.dingdong.model.UploadSingleResult;
import com.vinatti.dingdong.utiles.Constants;
import com.vinatti.dingdong.utiles.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryStatistic(fromDate, status, postmanId,shift);
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
                                             String shiftID, CommonCallback<CommonObjectListResult> callback) {
        Call<CommonObjectListResult> call = getAPIBuilder().searchDeliveryPostman(postmanID, fromDate, shiftID);
        call.enqueue(callback);
    }

    public static void searchParcelCodeDelivery(String parcelCode, CommonCallback<CommonObjectResult> callback) {
        String signature = Utils.SHA256(parcelCode.toUpperCase() + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<CommonObjectResult> call = getAPIBuilder().searchParcelCodeDelivery(parcelCode.toUpperCase(), signature);
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
                                         String signatureCapture, String note, String amount, String ladingPostmanID, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(ladingCode + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().pushToPNSDelivery(postmanID, ladingCode, deliveryPOCode, deliveryDate,
                deliveryTime, receiverName, reasonCode, solutionCode, status, paymentChannel, deliveryType,
                signatureCapture, note, amount, ladingPostmanID, Constants.SHIFT, signature);
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
                                       String collectAmount,
                                       CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().paymentDelivery(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, collectAmount, Constants.SHIFT, signature);
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
                                       String collectAmount,
                                       CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(parcelCode + mobileNumber + deliveryPOCode + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().paymentPaypost(postmanID,
                parcelCode, mobileNumber, deliveryPOCode, deliveryDate, deliveryTime, receiverName, receiverIDNumber, reasonCode, solutionCode,
                status, paymentChannel, deliveryType, signatureCapture,
                note, collectAmount, Constants.SHIFT, signature);
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
                                                  String pickUpTime, String file, String scan, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().collectOrderPostmanCollect(employeeID, orderID, orderPostmanID,
                statusCode, quantity, collectReason, pickUpDate, pickUpTime, file, scan);
        call.enqueue(callback);
    }


    public static void searchCreateBd13(String deliveryPOCode, String routePOCode, String bagNumber, String chuyenThu, String createDate, String shift, CommonCallback<HistoryCreateBd13Result> commonCallback) {
        Call<HistoryCreateBd13Result> call = getAPIBuilder().searchCreateBd13(deliveryPOCode, routePOCode, bagNumber, chuyenThu, createDate, shift);
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
}

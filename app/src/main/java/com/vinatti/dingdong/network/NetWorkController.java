package com.vinatti.dingdong.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.ActiveResult;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.XacNhanTinResult;
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
                                                 String toAssignDate, CommonCallback<XacNhanTinResult> callback) {
        Call<XacNhanTinResult> call = getAPIBuilder().searchOrderPostmanCollect(orderPostmanID, orderID, postmanID, status, fromAssignDate, toAssignDate);
        call.enqueue(callback);
    }

    public static void validationAuthorized(String mobileNumber, CommonCallback<SimpleResult> callback) {
        String signature = Utils.SHA256(mobileNumber + BuildConfig.PRIVATE_KEY).toUpperCase();
        Call<SimpleResult> call = getAPIBuilder().validationAuthorized(mobileNumber, signature);
        call.enqueue(callback);
    }

    public static void getPostOfficeByCode(String code, CommonCallback<PostOfficeResult> callback) {
        Call<PostOfficeResult> call = getAPIBuilder().getPostOfficeByCode(code);
        call.enqueue(callback);
    }

    public static void confirmOrderPostmanCollect(String orderPostmanID, String employeeID,
                                                  String statusCode, String confirmReason, CommonCallback<SimpleResult> callback) {
        Call<SimpleResult> call = getAPIBuilder().confirmOrderPostmanCollect(orderPostmanID, employeeID, statusCode, confirmReason);
        call.enqueue(callback);
    }


}

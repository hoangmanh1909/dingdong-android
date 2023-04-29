package com.ems.dingdong.network.api;

import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.callback.CommonCallback;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.CallTomeRequest;
import com.ems.dingdong.model.DeliveryRefundRequest;
import com.ems.dingdong.model.LadingRefundDetailRespone;
import com.ems.dingdong.model.LadingRefundTotalRequest;
import com.ems.dingdong.model.LadingRefundTotalRespone;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.TicketManagementTotalRequest;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.ActiveRequest;
import com.ems.dingdong.model.request.AuthGetVersionRequest;
import com.ems.dingdong.model.request.GetPostOfficeByCodeRequest;
import com.ems.dingdong.model.request.LoginRequest;
import com.ems.dingdong.model.request.RequestObject;
import com.ems.dingdong.model.request.StatisticPaymentRequest;
import com.ems.dingdong.model.request.ValidationRequest;
import com.ems.dingdong.utiles.Constants;
import com.ems.dingdong.utiles.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    static String privateKeyAuthorization = "Basic RGluZ0Rvbmc6R2F0ZXdheUAyMTIyIUAj";
    private static volatile VinattiApi apiRxBuilder;
    public static Interceptor interceptor = chain -> {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Authorization", privateKeyAuthorization);
        return chain.proceed(builder.build());
    };

    public static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    public static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor);


    private static VinattiApi getAPIRxBuilder() {
        if (apiRxBuilder == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_DINGDONG_GATEWAY)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiRxBuilder = retrofit.create(VinattiApi.class);
        }
        return apiRxBuilder;
    }

    public static Observable<LoginResult> ddLogin(LoginRequest loginRequest) {
        String data = getGson().toJson(loginRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.AUTH_LOGIN,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().executeLogin(requestObject);
    }

    public static Observable<SimpleResult> ddXacThuc(ValidationRequest validationRequest) {
        String data = getGson().toJson(validationRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.AUTH_VALIDATION,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddLoginSms(ActiveRequest activeRequest) {
        String data = getGson().toJson(activeRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.AUTH_ACTIVE,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddGetVersion() {
        AuthGetVersionRequest authGetVersionRequest = new AuthGetVersionRequest("DD_ANDROID");
        String data = getGson().toJson(authGetVersionRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.AUTH_GET_VERSION,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddPostOfficeByCode(GetPostOfficeByCodeRequest getPostOfficeByCodeRequest) {
        String data = getGson().toJson(getPostOfficeByCodeRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.AUTH_GET_POST_OFFICE_BY_CODE,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddGetPaymentStatistic(StatisticPaymentRequest statisticPaymentRequest) {
        String data = getGson().toJson(statisticPaymentRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.STT_PAYMENT,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddGetTienHome(BalanceModel balanceModel) {
        String data = getGson().toJson(balanceModel);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.STT_POSTMAN_GET_BALANCE,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddCallToMe(CallTomeRequest callTomeRequest) {
        String data = getGson().toJson(callTomeRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.DLV_CALL_TO_ME,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddTicketDen(TicketManagementTotalRequest callTomeRequest) {
        String data = getGson().toJson(callTomeRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.STT_TICKET_MANAGEMENT_TOTAL_V2,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddGetListLadingCoe(List<String> callTomeRequest) {
        String data = getGson().toJson(callTomeRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.STT_TICKET_MANAGEMENT_DETAIL_V2,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddLadingRefundTotal(LadingRefundTotalRequest ladingRefundTotalRequest) {
        String data = getGson().toJson(ladingRefundTotalRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.STT_LADING_REFUND_TOTAL,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddLadingRefundDetail(LadingRefundTotalRequest ladingRefundTotalRequest) {
        String data = getGson().toJson(ladingRefundTotalRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.STT_LADING_REFUND_DETAIL,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddGetLadingRefundDetail(LadingRefundDetailRespone ladingRefundTotalRequest) {
        String data = getGson().toJson(ladingRefundTotalRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.STT_GET_LADING_REFUND_DETAIL,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<SimpleResult> ddUpdateLadingRefundDetail(DeliveryRefundRequest ladingRefundTotalRequest) {
        String data = getGson().toJson(ladingRefundTotalRequest);
        String signature = verifyRSAsha256.main(data);
        RequestObject requestObject =
                new RequestObject(
                        "ANDROID", "",
                        Constants.DLV_UPDATE_LADING_REFUND_DETAIL,
                        data,
                        Utils.getLocalTime(Constants.DATE_FORMAT),
                        "",
                        signature);
        return getAPIRxBuilder().execute(requestObject);
    }

    public static Observable<UploadSingleResult> postImageImageSignature(String filePath) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", "file_avatar.jpg", reqFile);
        ///MultipartBody.Part bodyAvatar = MultipartBody.Part.createFormData("avatar", "file_selfie_avatar.jpg", reqFile);///
        //MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        return getAPIRxBuilder().postImageImageSignature(body);
        ///callAvatar.enqueue(callback);///
    }

    public static Observable<UploadSingleResult> postImageSingle(String filePath) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", "file_avatar.jpg", reqFile);
        ///MultipartBody.Part bodyAvatar = MultipartBody.Part.createFormData("avatar", "file_selfie_avatar.jpg", reqFile);///
        //MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);
        return getAPIRxBuilder().postImageImage(body);
        ///callAvatar.enqueue(callback);///
    }
    public static Observable<UploadSingleResult> postImageAvatar(String filePath) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part bodyAvatar = MultipartBody.Part.createFormData("image_avatar", "file_selfie_avatar.jpg", reqFile);
        return getAPIRxBuilder().postImageImage(bodyAvatar);
    }



//    public static Observable<SimpleResult> ddTicketManagementTotal(CallTomeRequest callTomeRequest) {
//        String data = getGson().toJson(callTomeRequest);
//        String signature = verifyRSAsha256.main(data);
//        RequestObject requestObject =
//                new RequestObject(
//                        "ANDROID", "",
//                        Constants.DLV_CALL_TO_ME,
//                        data,
//                        Utils.getLocalTime(Constants.DATE_FORMAT),
//                        "",
//                        signature);
//        return getAPIRxBuilder().execute(requestObject);
//    }
}

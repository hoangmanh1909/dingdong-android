package com.vinatti.dingdong.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vinatti.dingdong.BuildConfig;
import com.vinatti.dingdong.callback.CommonCallback;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.SimpleResult;
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
        String signature = Utils.SHA256(mobileNumber + signCode + BuildConfig.PRIVATE_KEY);
        Call<LoginResult> call = getAPIBuilder().loginAuthorized(mobileNumber, signCode, signature);
        call.enqueue(callback);
    }


}

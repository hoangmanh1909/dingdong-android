package com.ems.dingdong.network.vmap;

import static com.ems.dingdong.utiles.Utils.getUnsafeOkHttpClient;
import static com.ems.dingdong.utiles.Utils.getUnsafeOkHttpClientSSL;

import com.ems.dingdong.BuildConfig;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.vmapmode.CreateUserVmap;
import com.ems.dingdong.model.vmapmode.LoginVmap;
import com.ems.dingdong.model.vmapmode.PushGPSVmap;
import com.ems.dingdong.model.vmapmode.respone.CreateUserVmapRespone;
import com.ems.dingdong.model.vmapmode.respone.LoginResult;
import com.ems.dingdong.network.VinattiAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.poi.ss.formula.functions.T;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkVmapController {

    private static volatile VmapAPI apiRxBuilder;


    public static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }


    private static VmapAPI getAPIRxBuilder() {
        if (apiRxBuilder == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_VMAP)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClientSSL(120, 120, ""))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiRxBuilder = retrofit.create(VmapAPI.class);
        }
        return apiRxBuilder;
    }


    public static Single<LoginResult> ddLoginVmap(LoginVmap request) {
        return getAPIRxBuilder().ddLoginVmap(request);
    }

    public static Single<CreateUserVmapRespone> ddCreateUserVmap(CreateUserVmap request, String token) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_VMAP)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .client(getUnsafeOkHttpClientSSL(120, 120, token))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(VmapAPI.class).ddCreateUserVmap(request);
    }

    public static Single<T> ddPushGPSVmap(PushGPSVmap request, String id, String token) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_VMAP)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .client(getUnsafeOkHttpClientSSL(120, 120, token))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(VmapAPI.class).ddPushGPSVmap(id, request);
    }
}

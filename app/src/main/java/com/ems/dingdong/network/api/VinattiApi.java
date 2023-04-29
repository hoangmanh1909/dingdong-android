package com.ems.dingdong.network.api;


import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.request.RequestObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface VinattiApi {

    @POST("Gateway/Execute")
    Observable<SimpleResult> execute(@Body RequestObject loginRequest);

    @POST("Gateway/Execute")
    Observable<LoginResult> executeLogin(@Body RequestObject loginRequest);

    @Multipart
    @POST("API/Handle/UploadSignature")
    Observable<UploadSingleResult> postImageImageSignature(@Part MultipartBody.Part image);

    @Multipart
    @POST("api/Handle/UploadImage")
    Observable<UploadSingleResult> postImageImage(@Part MultipartBody.Part image);

}

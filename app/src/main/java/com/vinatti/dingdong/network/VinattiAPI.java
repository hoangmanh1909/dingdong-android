package com.vinatti.dingdong.network;


import com.vinatti.dingdong.model.ActiveResult;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.XacNhanTinResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HungNX on 6/30/16.
 */
public interface VinattiAPI {
    @FormUrlEncoded
    @POST("api/Authorized/Login")
    Call<LoginResult> loginAuthorized(@Field("MobileNumber") String mobileNumber,
                                      @Field("SignCode") String signCode,
                                      @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/Authorized/Active")
    Call<ActiveResult> activeAuthorized(@Field("MobileNumber") String mobileNumber,
                                        @Field("ActiveCode") String activeCode,
                                        @Field("CodeDeviceActive") String codeDeviceActive,
                                        @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/Collect/SearchOrderPostman")
    Call<XacNhanTinResult> searchOrderPostmanCollect(@Field("OrderPostmanID") String orderPostmanID,
                                                     @Field("OrderID") String orderID,
                                                     @Field("PostmanID") String postmanID,
                                                     @Field("Status") String status,
                                                     @Field("FromAssignDate") String fromAssignDate,
                                                     @Field("ToAssignDate") String toAssignDate

    );

    @FormUrlEncoded
    @POST("api/Collect/ConfirmOrderPostman")
    Call<SimpleResult> confirmOrderPostmanCollect(@Field("OrderPostmanID") String orderPostmanID,
                                                  @Field("EmployeeID") String employeeID,
                                                  @Field("StatusCode") String statusCode,
                                                  @Field("ConfirmReason") String confirmReason
    );

    @FormUrlEncoded
    @POST("api/Authorized/Validation")
    Call<SimpleResult> validationAuthorized(@Field("MobileNumber") String mobileNumber,
                                            @Field("Signature") String signature
    );

    @GET("api/Dictionary/GetPostOfficeByCode")
    Call<PostOfficeResult> getPostOfficeByCode(@Query("code") String unitCode);


    @POST("API/TaskOfWork/Create")
    Call<SimpleResult> taskOfWork(@Body SimpleResult taskRequest);

}
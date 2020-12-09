package com.ems.dingdong.network;


import com.ems.dingdong.model.CallHistoryVHT;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.request.BankAccountNumberRequest;
import com.ems.dingdong.model.request.SeaBankInquiryRequest;
import com.ems.dingdong.model.request.SeaBankPaymentRequest;
import com.ems.dingdong.model.response.BankAccountNumberResponse;
import com.ems.dingdong.model.response.IdentifyCationResponse;
import com.ems.dingdong.model.response.SeaBankHistoryPaymentResponse;
import com.ems.dingdong.model.response.SeaBankInquiryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HungNX on 6/30/16.
 */
public interface ChiHoBtxhAPI {

    @POST("api/SeaBank/GetBankAccountNumber")
    Call<BankAccountNumberResponse> getBankAccountNumber(@Body BankAccountNumberRequest bankAccountNumberRequest);



    @POST("api/SeaBank/Inquiry")
    Call<SeaBankInquiryResponse> seaBankInquiry(@Body SeaBankInquiryRequest seaBankInquiryRequest);

    @GET("api/SeaBank/GetIdentifyCation")
    Call<IdentifyCationResponse> getIdentifyCation();

    @POST("api/SeaBank/Payment")
    Call<SimpleResult> seaBankPayment(@Body SeaBankPaymentRequest seaBankPaymentRequest);

    @GET("api/SeaBank/GetHistoryPayment")
    Call<SeaBankHistoryPaymentResponse> getHistoryPaymentSeaBank(@Query("MobileNumber") String mobileNumber,
                                                                 @Query("FromDate") String fromDate,
                                                                 @Query("ToDate") String toDate);

    //
    /*@POST("")
    Call<> postInfoCall(@Field("numberCaller") String numberCaller,
                        @Field("numberCallee") String numberCallee);*/

}
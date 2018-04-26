package com.vinatti.dingdong.network;


import com.vinatti.dingdong.model.ActiveResult;
import com.vinatti.dingdong.model.CommonObjectResult;
import com.vinatti.dingdong.model.HistoryCallResult;
import com.vinatti.dingdong.model.LoginResult;
import com.vinatti.dingdong.model.PostOfficeResult;
import com.vinatti.dingdong.model.ReasonResult;
import com.vinatti.dingdong.model.SimpleResult;
import com.vinatti.dingdong.model.SolutionResult;
import com.vinatti.dingdong.model.CommonObjectListResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @POST("api/Delivery/DeliveryStatistic")
    Call<CommonObjectListResult> searchDeliveryStatistic(@Field("FromDate") String fromDate,
                                                         @Field("Status") String status,
                                                         @Field("PostmanId") String postmanId
    );

    @FormUrlEncoded
    @POST("api/Delivery/DeliveryLadingJourney")
    Call<CommonObjectListResult> getHistoryDelivery(@Field("ParcelCode") String parcelCode);

    @FormUrlEncoded
    @POST("api/Authorized/Active")
    Call<ActiveResult> activeAuthorized(@Field("MobileNumber") String mobileNumber,
                                        @Field("ActiveCode") String activeCode,
                                        @Field("CodeDeviceActive") String codeDeviceActive,
                                        @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/CallCenter/CallForward")
    Call<SimpleResult> callForwardCallCenter(@Field("CallerNumber") String callerNumber,
                                             @Field("CalleeNumber") String calleeNumber,
                                             @Field("CallForwardType") String callForwardType,
                                             @Field("HotlineNumber") String hotlineNumber,
                                             @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/Collect/SearchOrderPostman")
    Call<CommonObjectListResult> searchOrderPostmanCollect(@Field("OrderPostmanID") String orderPostmanID,
                                                           @Field("OrderID") String orderID,
                                                           @Field("PostmanID") String postmanID,
                                                           @Field("Status") String status,
                                                           @Field("FromAssignDate") String fromAssignDate,
                                                           @Field("ToAssignDate") String toAssignDate);

    @FormUrlEncoded
    @POST("api/Delivery/DeliveryPostman")
    Call<CommonObjectListResult> searchDeliveryPostman(@Field("PostmanId") String postmanID,
                                                       @Field("FromDate") String fromDate,
                                                       @Field("ShiftID") String shiftID
    );

    @FormUrlEncoded
    @POST("api/Collect/CollectOrderPostman")
    Call<SimpleResult> collectOrderPostmanCollect(@Field("EmployeeID") String employeeID,
                                                  @Field("OrderID") String orderID,
                                                  @Field("OrderPostmanID") String orderPostmanID,
                                                  @Field("StatusCode") String statusCode,
                                                  @Field("Quantity") String quantity,
                                                  @Field("CollectReason") String collectReason,
                                                  @Field("PickUpDate") String pickUpDate,
                                                  @Field("PickUpTime") String pickUpTime);

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

    @FormUrlEncoded
    @POST("api/Delivery/CheckLadingCode")
    Call<SimpleResult> checkLadingCode(@Field("ParcelCode") String mobileNumber,
                                       @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/Delivery/Inquiry")
    Call<CommonObjectResult> searchParcelCodeDelivery(@Field("ParcelCode") String parcelCode,
                                                      @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/TrackTrace/Lading")
    Call<CommonObjectResult> findLocation(@Field("LadingCode") String ladingCode,
                                          @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/Delivery/PushToPNS")
    Call<SimpleResult> pushToPNSDelivery(@Field("PostmanID") String postmanID,
                                         @Field("LadingCode") String ladingCode,
                                         @Field("DeliveryPOCode") String deliveryPOCode,
                                         @Field("DeliveryDate") String deliveryDate,
                                         @Field("DeliveryTime") String deliveryTime,
                                         @Field("ReceiverName") String receiverName,
                                         @Field("ReasonCode") String reasonCode,
                                         @Field("SolutionCode") String solutionCode,
                                         @Field("Status") String status,
                                         @Field("PaymentChannel") String paymentChannel,
                                         @Field("DeliveryType") String deliveryType,
                                         @Field("SignatureCapture") String signatureCapture,
                                         @Field("Note") String note,
                                         @Field("Signature") String signature);

    @FormUrlEncoded
    @POST("api/CallCenter/AddNew")
    Call<SimpleResult> addNewCallCenter(@Field("AmndEmp") String amndEmp,
                                        @Field("PostmanID") String postmanID,
                                        @Field("SenderPhone") String senderPhone,
                                        @Field("ReceiverPhone") String receiverPhone,
                                        @Field("CallTime") String callTime,
                                        @Field("CreatedDate") String createdDate,
                                        @Field("Signature") String signature);

    @FormUrlEncoded
    @POST("api/CallCenter/Search")
    Call<HistoryCallResult> searchCallCenter(@Field("PostmanID") String postmanID,
                                             @Field("FromDate") String fromDate,
                                             @Field("ToDate") String toDate,
                                             @Field("Signature") String signature);

    @FormUrlEncoded
    @POST("api/Delivery/Payment")
    Call<SimpleResult> paymentDelivery(@Field("PostmanID") String postmanID,
                                       @Field("ParcelCode") String parcelCode,
                                       @Field("MobileNumber") String mobileNumber,
                                       @Field("DeliveryPOCode") String deliveryPOCode,
                                       @Field("DeliveryDate") String deliveryDate,
                                       @Field("DeliveryTime") String deliveryTime,
                                       @Field("ReceiverName") String receiverName,
                                       @Field("ReceiverIDNumber") String receiverIDNumber,
                                       @Field("ReasonCode") String reasonCode,
                                       @Field("SolutionCode") String solutionCode,
                                       @Field("Status") String status,
                                       @Field("PaymentChannel") String paymentChannel,
                                       @Field("DeliveryType") String deliveryType,
                                       @Field("SignatureCapture") String signatureCapture,
                                       @Field("Note") String note,
                                       @Field("Signature") String signature);

    @GET("api/Dictionary/GetPostOfficeByCode")
    Call<PostOfficeResult> getPostOfficeByCode(@Query("code") String unitCode);

    @GET("api/Dictionary/GetSolutionByReasonCode")
    Call<SolutionResult> getSolutionByReasonCode(@Query("reasonCode") String reasonCode);

    @GET("api/Dictionary/GetReasons")
    Call<ReasonResult> getReasons();


    @POST("API/TaskOfWork/Create")
    Call<SimpleResult> taskOfWork(@Body SimpleResult taskRequest);

}
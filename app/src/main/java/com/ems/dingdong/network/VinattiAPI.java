package com.ems.dingdong.network;


import com.ems.dingdong.model.ActiveResult;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.HistoryCallResult;
import com.ems.dingdong.model.HistoryCreateBd13Result;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.LoginResult;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.StatisticCollectResult;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.model.StatisticDebitGeneralResult;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.UploadResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.request.vietmap.RouteRequest;
import com.ems.dingdong.model.request.vietmap.UpdateRequest;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
                                                         @Field("ToDate") String toDate,
                                                         @Field("Status") String status,
                                                         @Field("PostmanId") String postmanId,
                                                         @Field("ShiftID") String shift,
                                                         @Field("RouteCode") String routeCode);

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
                                             @Field("LadingCode") String ladingCode,
                                             @Field("Type") String type,
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
    @POST("api/Collect/SearchOrderPostmanCollectAll")
    Call<CommonObjectListResult> searchAllOrderPostmanCollect(@Field("OrderPostmanID") String orderPostmanID,
                                                              @Field("OrderID") String orderID,
                                                              @Field("PostmanID") String postmanID,
                                                              @Field("Status") String status,
                                                              @Field("FromAssignDate") String fromAssignDate,
                                                              @Field("ToAssignDate") String toAssignDate);

    @FormUrlEncoded
    @POST("api/Delivery/DeliveryPostman")
    Call<DeliveryPostmanResponse> searchDeliveryPostman(@Field("PostmanId") String postmanID,
                                                        @Field("FromDate") String fromDate,
                                                        @Field("ToDate") String toDate,
                                                        @Field("ShiftID") String shiftID,
                                                        @Field("Status") String status,
                                                        @Field("ChThu") String chuyenthu,
                                                        @Field("TuiSo") String tuiso,
                                                        @Field("RouteCode") String routeCode);


    @FormUrlEncoded
    @POST("api/Collect/ConfirmOrderPostman")
    Call<SimpleResult> confirmOrderPostmanCollect(@Field("OrderPostmanID") String orderPostmanID,
                                                  @Field("EmployeeID") String employeeID,
                                                  @Field("StatusCode") String statusCode,
                                                  @Field("ConfirmReason") String confirmReason);

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
    @POST("api/Delivery/InquiryAmount")
    Call<InquiryAmountResult> getInquiryAmount(@Field("ParcelCode") String parcelCode,
                                               @Field("Signature") String signature
    );

    @FormUrlEncoded
    @POST("api/TrackTrace/Lading")
    Call<CommonObjectResult> findLocation(@Field("LadingCode") String ladingCode,
                                          @Field("Signature") String signature
    );

    @POST("api/Delivery/PushToPNS")
    Call<SimpleResult> pushToPNSDelivery(@Body PushToPnsRequest request);

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

    @POST("api/Delivery/Payment")
    Call<SimpleResult> paymentDelivery(@Body PaymentDeviveryRequest request);

    @POST("api/Delivery/PaymentPaypost")
    Call<SimpleResult> paymentPaypost(@Body PaymentPaypostRequest request);


    @GET("api/Delivery/GetPaypostError")
    Call<GachNoResult> deliveryGetPaypostError(@Query("fromDate") String fromDate,
                                               @Query("toDate") String toDate);

    @GET("api/Dictionary/GetPostmanShift")
    Call<ShiftResult> getPostmanShift(@Query("poCode") String poCode);


    @GET("api/Dictionary/GetPostOfficeByCode")
    Call<PostOfficeResult> getPostOfficeByCode(@Query("code") String unitCode, @Query("postmanID") String postmanID);

    @GET("api/Dictionary/GetSolutionByReasonCode")
    Call<SolutionResult> getSolutionByReasonCode(@Query("reasonCode") String reasonCode);

    @GET("api/Dictionary/GetSolutions")
    Call<SolutionResult> getSolutions();

    @GET("api/Dictionary/GetReasons")
    Call<ReasonResult> getReasons();

    @GET("api/Dictionary/GetPickUpReasons")
    Call<ReasonResult> getReasonsHoanTat();

    @GET("api/Dictionary/GetCancelOrderReason")
    Call<ReasonResult> getReasonsHoanTatMiss();

    @POST("API/TaskOfWork/Create")
    Call<SimpleResult> taskOfWork(@Body SimpleResult taskRequest);

    @POST("api/DingDong/CreateBD13")
    Call<SimpleResult> addNewBD13(@Body Bd13Create taskRequest);

    @FormUrlEncoded
    @POST("api/BD13/Search")
    Call<HistoryCreateBd13Result> searchCreateBd13(@Field("DeliveryPOCode") String deliveryPOCode,
                                                   @Field("RoutePOCode") String routePOCode,
                                                   @Field("BagNumber") String bagNumber,
                                                   @Field("ChuyenThu") String chuyenThu,
                                                   @Field("CreateDate") String createDate,
                                                   @Field("Shift") String shift);

    @FormUrlEncoded
    @POST("Location/AddNew")
    Call<SimpleResult> locationAddNew(@Field("PostmanID") String postmanID,
                                      @Field("Latitude") String latitude,
                                      @Field("Longitude") String longitude,
                                      @Field("Signature") String signature);

    @FormUrlEncoded
    @POST("api/Delivery/UpdateMobile")
    Call<SimpleResult> updateMobile(@Field("Code") String code,
                                    @Field("MobileNumber") String mobileNumber);

    @Multipart
    @POST("api/Handle/UploadImage")
    Call<UploadResult> postImage(@Part MultipartBody.Part image);

    @Multipart
    @POST("api/Handle/UploadImage")
    Call<UploadSingleResult> postImageSingle(@Part MultipartBody.Part image);


    @POST("api/Collect/ConfirmAllOrderPostman")
    Call<ConfirmAllOrderPostmanResult> confirmAllOrderPostman(@Body ArrayList<ConfirmOrderPostman> request);


    @POST("api/Collect/CollectOrderPostman")
    Call<SimpleResult> collectOrderPostmanCollect(@Body HoanTatTinRequest hoanTatTinRequest);

    @FormUrlEncoded
    @POST("api/Statistic/Collect")
    Call<StatisticCollectResult> searchStatisticCollect(@Field("PostmanId") String postmanID,
                                                        @Field("FromDate") String fromDate,
                                                        @Field("ToDate") String toDate);

    @POST("api/Collect/CollectAllOrderPostman")
    Call<SimpleResult> collectAllOrderPostman(@Body List<HoanTatTinRequest> list);

    @FormUrlEncoded
    @POST("api/Statistic/DeliveryGeneral")
    Call<StatisticDeliveryGeneralResult> statisticDeliveryGeneral(@Field("PostmanId") String postmanID,
                                                                  @Field("FromDate") String fromDate,
                                                                  @Field("ToDate") String toDate,
                                                                  @Field("IsSuccess") boolean isSuccess,
                                                                  @Field("RouteCode") String routeCode);

    @FormUrlEncoded
    @POST("api/Statistic/DeliveryDetail")
    Call<StatisticDeliveryDetailResult> statisticDeliveryDetail(@Field("ServiceCode") String serviceCode,
                                                                @Field("Type") int typeDelivery,
                                                                @Field("PostmanId") String postmanID,
                                                                @Field("FromDate") String fromDate,
                                                                @Field("ToDate") String toDate,
                                                                @Field("IsSuccess") boolean isSuccess,
                                                                @Field("RouteCode") String routeCode);

    @FormUrlEncoded
    @POST("api/Statistic/DebitGeneral")
    Call<StatisticDebitGeneralResult> statisticDebitGeneral(@Field("PostmanId") String postmanID,
                                                            @Field("FromDate") String fromDate,
                                                            @Field("ToDate") String toDate,
                                                            @Field("RouteCode") String routeCode);

    @FormUrlEncoded
    @POST(" api/Statistic/DebitDetail")
    Call<StatisticDebitDetailResult> statisticDebitDetail(@Field("PostmanId") String postmanID,
                                                          @Field("FromDate") String fromDate,
                                                          @Field("ToDate") String toDate,
                                                          @Field("StatusCode") String statusCode,
                                                          @Field("RouteCode") String routeCode);

    @FormUrlEncoded
    @POST("api/DingDong/GetMainview")
    Call<HomeCollectInfoResult> getHomeData(@Field("FromDate") String fromDate,
                                            @Field("ToDate") String toDate,
                                            @Field("PostmanCode") String postmanCode,
                                            @Field("RouteCode") String routeCode);

    @POST("api/DingDong/GetLadingCreateBD13")
    Call<DeliveryPostmanResponse> searchLadingCreatedBd13(@Body DingDongGetLadingCreateBD13Request request);

    @FormUrlEncoded
    @POST("api/DingDong/GetCancelDelivery")
    Call<DingDongGetCancelDeliveryResponse> getCancelDelivery(
            @Field("PostmanCode") String postmanCode,
            @Field("RouteCode") String routeCode,
            @Field("LadingCode") String ladingCode,
            @Field("FromDate") String fromDate,
            @Field("ToDate") String toDate);

    @POST("api/DingDong/CancelDelivery")
    Call<SimpleResult> cancelDelivery(@Body List<DingDongCancelDeliveryRequest> taskRequest);

    @GET("api/Dictionary/GetDeliveryRoute")
    Call<RouteInfoResult> getDeliveryRoute(@Query("poCode") String poCode);

    @GET("api/Dictionary/GetPostmanByRoute")
    Call<UserInfoResult> getPostmanByRoute(@Query("poCode") String poCode,
                                           @Query("routeId") Integer routeId,
                                           @Query("routeType") String routeType);

    @POST("api/DingDong/CancelDivided")
    Call<SimpleResult> cancelDivided(@Body List<DingDongCancelDividedRequest> taskRequest);

    @GET("api/VietMap/Reverse")
    Call<XacMinhDiaChiResult> getAddressByLocation(@Query("longitude") double longitude,
                                                   @Query("latitude") double latitude);

    @FormUrlEncoded
    @POST("api/VietMap/Verify")
    Call<SimpleResult> vietmapVerify(@Field("Id") String id,
                                     @Field("IdUser") String idUser,
                                     @Field("IsVerify") boolean isVerify,
                                     @Field("Layer") String layer);

    @POST("api/VietMap/Update")
    Call<SimpleResult> vietmapUpdate(@Body UpdateRequest taskRequest);

    @FormUrlEncoded
    @POST("api/Statistic/LadingStatusGeneral")
    Call<StatisticDeliveryGeneralResult> getLadingStatusGeneral(@Field("PostmanId") String postmanID,
                                                                @Field("FromDate") String fromDate,
                                                                @Field("ToDate") String toDate,
                                                                @Field("LadingType") int ladingType,
                                                                @Field("RouteCode") String routeCode);

    @FormUrlEncoded
    @POST("api/Statistic/LadingStatusDetail")
    Call<StatisticDeliveryDetailResult> getLadingStatusDetail(@Field("Type") int type,
                                                              @Field("ServiceCode") String serviceCode,
                                                              @Field("PostmanId") String postmanID,
                                                              @Field("FromDate") String fromDate,
                                                              @Field("ToDate") String toDate,
                                                              @Field("LadingType") int ladingType,
                                                              @Field("RouteCode") String routeCode);

    @GET("api/VietMap/Search")
    Call<XacMinhDiaChiResult> vietmapSearch(@Query("text") String text);

    @POST("api/VietMap/Route")
    Call<XacMinhDiaChiResult> vietmapRoute(@Body List<RouteRequest> taskRequest);

    @FormUrlEncoded
    @POST("api/Statistic/Payment")
    Call<StatisticPaymentResult> statisticPayment(@Field("PostmanId") String postmanID,
                                                  @Field("POCode") String poCode,
                                                  @Field("PostmanMobileNumber") String phoneNumber,
                                                  @Field("FromDate") String fromDate,
                                                  @Field("ToDate") String toDate);

}
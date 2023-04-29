package com.ems.dingdong.network.vmap;


import com.ems.dingdong.functions.mainhome.main.data.CallLogMode;
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.OrderCreateBD13Mode;
import com.ems.dingdong.model.ActiveResult;
import com.ems.dingdong.model.BalanceModel;
import com.ems.dingdong.model.Bd13Create;
import com.ems.dingdong.model.CallHistoryVHT;
import com.ems.dingdong.model.CallLiveMode;
import com.ems.dingdong.model.CancelDeliveryResult;
import com.ems.dingdong.model.ChangeRouteResult;
import com.ems.dingdong.model.CommonObjectListResult;
import com.ems.dingdong.model.CommonObjectResult;
import com.ems.dingdong.model.ConfirmAllOrderPostmanResult;
import com.ems.dingdong.model.ConfirmOrderPostman;
import com.ems.dingdong.model.ConfirmOrderPostmanResult;
import com.ems.dingdong.model.CreateVietMapRequest;
import com.ems.dingdong.model.DataRequestPayment;
import com.ems.dingdong.model.DecodeDiaChiResult;
import com.ems.dingdong.model.DeliveryCheckAmountPaymentResult;
import com.ems.dingdong.model.DeliverySuccessRequest;
import com.ems.dingdong.model.DingDongCancelDividedRequest;
import com.ems.dingdong.model.EWalletDataResult;
import com.ems.dingdong.model.EWalletRequestResult;
import com.ems.dingdong.model.GachNoResult;
import com.ems.dingdong.model.HistoryCallResult;
import com.ems.dingdong.model.HistoryCreateBd13Result;
import com.ems.dingdong.model.HomeCollectInfoResult;
import com.ems.dingdong.model.InquiryAmountResult;
import com.ems.dingdong.model.LinkEWalletResult;
import com.ems.dingdong.model.LinkHistory;
import com.ems.dingdong.model.PostOfficeResult;
import com.ems.dingdong.model.ReasonResult;
import com.ems.dingdong.model.ReceiverVpostcodeMode;
import com.ems.dingdong.model.RouteInfoResult;
import com.ems.dingdong.model.RouteResult;
import com.ems.dingdong.model.SenderVpostcodeMode;
import com.ems.dingdong.model.ShiftResult;
import com.ems.dingdong.model.SimpleResult;
import com.ems.dingdong.model.SolutionResult;
import com.ems.dingdong.model.StatisticCollectResult;
import com.ems.dingdong.model.StatisticDebitDetailResult;
import com.ems.dingdong.model.StatisticDebitGeneralResult;
import com.ems.dingdong.model.StatisticDeliveryDetailResult;
import com.ems.dingdong.model.StatisticDeliveryGeneralResult;
import com.ems.dingdong.model.StatisticPaymentResult;
import com.ems.dingdong.model.ThuGomRespone;
import com.ems.dingdong.model.TokenMoveCropResult;
import com.ems.dingdong.model.UploadResult;
import com.ems.dingdong.model.UploadSingleResult;
import com.ems.dingdong.model.UserInfoResult;
import com.ems.dingdong.model.VerifyLinkOtpResult;
import com.ems.dingdong.model.XacMinhDiaChiResult;
import com.ems.dingdong.model.XacMinhRespone;
import com.ems.dingdong.model.request.CancelDeliveryStatisticRequest;
import com.ems.dingdong.model.request.ChangeRouteRequest;
import com.ems.dingdong.model.request.DeliveryPaymentV2;
import com.ems.dingdong.model.request.DeliveryProductRequest;
import com.ems.dingdong.model.request.DeliveryUnSuccessRequest;
import com.ems.dingdong.model.request.DingDongCancelDeliveryRequest;
import com.ems.dingdong.model.request.DingDongGetLadingCreateBD13Request;
import com.ems.dingdong.model.request.HoanTatTinRequest;
import com.ems.dingdong.model.request.PayLinkConfirm;
import com.ems.dingdong.model.request.PayLinkRequest;
import com.ems.dingdong.model.request.PaymentConfirmModel;
import com.ems.dingdong.model.request.PaymentDeviveryRequest;
import com.ems.dingdong.model.request.PaymentPaypostRequest;
import com.ems.dingdong.model.request.PaymentRequestModel;
import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.ems.dingdong.model.request.PushToPnsRequest;
import com.ems.dingdong.model.request.RequestObject;
import com.ems.dingdong.model.request.vietmap.UpdateRequest;
import com.ems.dingdong.model.response.DeliveryPostmanResponse;
import com.ems.dingdong.model.response.DingDongGetCancelDeliveryResponse;
import com.ems.dingdong.model.response.PaymentRequestResponse;
import com.ems.dingdong.model.response.VerifyAddressRespone;
import com.ems.dingdong.model.thauchi.SmartBankRequestCancelLinkRequest;
import com.ems.dingdong.model.vmapmode.CreateUserVmap;
import com.ems.dingdong.model.vmapmode.LoginVmap;
import com.ems.dingdong.model.vmapmode.PushGPSVmap;
import com.ems.dingdong.model.vmapmode.respone.CreateUserVmapRespone;
import com.ems.dingdong.model.vmapmode.respone.LoginResult;
import com.ems.dingdong.notification.cuocgoictel.data.HistoryRequest;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Khiem on 11/03/2023.
 */
public interface VmapAPI {

    @POST("api/track/auth/login")
    Single<LoginResult> ddLoginVmap(@Body LoginVmap request);

    @POST("api/track/postman?entityGroupId=5d614de0-b753-11ed-8106-c17923393d82")
    Single<CreateUserVmapRespone> ddCreateUserVmap(@Body CreateUserVmap request);

    @POST("api/track/telemetry/ASSET/{tag}/timeseries/ANY?scope=ANY")
    Single<T> ddPushGPSVmap(@Path("tag") String tag, @Body PushGPSVmap request);


}
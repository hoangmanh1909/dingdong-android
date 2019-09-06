package com.ems.dingdong.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeaBankHistoryPaymentModel {
    @SerializedName("ID")
    @Expose
    val id: Int? = null
    @SerializedName("MobileNumber")
    @Expose
    val mobileNumber: String? = null
    @SerializedName("StringInfo")
    @Expose
    val stringInfo: String? = null
    @SerializedName("StringHeader")
    @Expose
    val stringHeader: String? = null
    @SerializedName("StringValue")
    @Expose
    val stringValue: String? = null
    @SerializedName("SeaBankRetRefNumber")
    @Expose
    val seaBankRetRefNumber: String? = null
    @SerializedName("PayPostRetRefNumber")
    @Expose
    val payPostRetRefNumber: String? = null
    @SerializedName("OTPNumber")
    @Expose
    val otpNumber: String? = null
    @SerializedName("ReceiverName")
    @Expose
    val receiverName: String? = null
    @SerializedName("ReceiverAddress")
    @Expose
    val receiverAddress: String? = null
    @SerializedName("ReceiverPhoneNumber")
    @Expose
    val receiverPhoneNumber: String? = null
    @SerializedName("PIdType")
    @Expose
    val pIdType: String? = null
    @SerializedName("PIDNumber")
    @Expose
    val pIdNumber: String? = null
    @SerializedName("PIDDate")
    @Expose
    val pIdDate: String? = null
    @SerializedName("PIDWhere")
    @Expose
    val pIdWhere: String? = null
    @SerializedName("Reason")
    @Expose
    val reason: String? = null
    @SerializedName("PaymentStatus")
    @Expose
    val paymentStatus: String? = null

}

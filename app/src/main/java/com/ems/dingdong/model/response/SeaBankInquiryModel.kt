package com.ems.dingdong.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeaBankInquiryModel {
    @SerializedName("AssigneeName")
    @Expose
    val assigneeName: String? = null
    @SerializedName("IdentifyNumber")
    @Expose
    val identifyNumber: String? = null
    @SerializedName("Amount")
    @Expose
    val amount: String? = null
    @SerializedName("SeaBankRetRefNumber")
    @Expose
    val seaBankRetRefNumber: String? = null
    @SerializedName("StringInfo")
    @Expose
    val stringInfo: String? = null
    @SerializedName("StringHeader")
    @Expose
    val stringHeader: String? = null
    @SerializedName("StringValue")
    @Expose
    val stringValue: String? = null
    @SerializedName("MandatorName")
    @Expose
    val mandatorName: String? = null


}

package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class EWalletRemoveDataRequest(
    @SerializedName("LadingCode")
    val ladingCode:String?,
    @SerializedName("RetRefNumber")
    val retRefNumber :String?,
    @SerializedName("RemoveBy")
    val removeBy :Long?,
    @SerializedName("POCode")
    val pOCode :String?,
    @SerializedName("ReasonType")
    val reasonType :String?,
    @SerializedName("ReasonText")
    val reasonText :String?,
    @SerializedName("ServiceCode")
    val serviceCode :String?

)
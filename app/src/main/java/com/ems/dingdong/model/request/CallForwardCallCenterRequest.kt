package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class CallForwardCallCenterRequest(
    @SerializedName("CallerNumber")
    val callerNumber:String,
    @SerializedName("CalleeNumber")
    val calleeNumber:String,
    @SerializedName("CallForwardType")
    val callForwardType:String,
    @SerializedName("HotlineNumber")
    val hotlineNumber:String,
    @SerializedName("LadingCode")
    val ladingCode:String,
    @SerializedName("Type")
    val type:String,
    @SerializedName("PostmanId")
    val postmanId:String,
    @SerializedName("POcode")
    val pOcode:String,
    @SerializedName("Signature")
    val signature:String

)
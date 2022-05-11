package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

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
    @SerializedName("Signature")
    val signature:String

)
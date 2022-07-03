package com.ems.dingdong.model.response

import com.google.gson.annotations.SerializedName

data class PaymentRequestResponse(
    @SerializedName("Code")
    var code:String?,
    @SerializedName("Message")
    var message:String?,
    @SerializedName("Data")
    var data:String?,
    @SerializedName("Value")
    var value:EWalletRequestResponse?,
    @SerializedName("Time")
    var time:String?
)
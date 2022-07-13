package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

class ConfirmOrderPostmanCollectRequest(
    @SerializedName("OrderPostmanID")
    val orderPostmanID:String,
    @SerializedName("EmployeeID")
    val employeeID:String,
    @SerializedName("StatusCode")
    val statusCode:String,
    @SerializedName("ConfirmReason")
    val confirmReason:String,
    @SerializedName("SourceChanel")
    val sourceChanel:String
)
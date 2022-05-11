package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BalanceRequest(
    @SerializedName("MobileNumber")
    @Expose
    val mobileNumber: String?,
    @SerializedName("PostmanId ")
    @Expose
    val postmanId : String?
)
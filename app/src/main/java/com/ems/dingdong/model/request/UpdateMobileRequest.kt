package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class UpdateMobileRequest(
    @SerializedName("Code")
    val code:String?,
    @SerializedName("Type")
    val type:String?,
    @SerializedName("MobileNumber")
    val mobileNumber:String?
)
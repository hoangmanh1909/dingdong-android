package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class BaseRequest(
    @SerializedName("ID")
    var iD: Int?,
    @SerializedName("MobileNumber")
    val mobileNumber: String?,
    @SerializedName("Code")
    val code: String?,
    @SerializedName("PoCode")
    val poCode: String?
)
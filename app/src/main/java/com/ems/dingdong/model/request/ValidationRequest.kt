package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ValidationRequest(
    @SerializedName("MobileNumber")
    @Expose
    val mobileNumber: String?,
    @SerializedName("Signature")
    @Expose
    val signature: String?
)
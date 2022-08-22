package com.ems.dingdong.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("MobileNumber")
    @Expose
    val mobileNumber: String?,
    @SerializedName("SignCode")
    @Expose
    val signCode: String?,
    @SerializedName("AppVersion")
    @Expose
    val appVersion: String?,
    @SerializedName("AppCode")
    @Expose
    val appCode: String?,
    @SerializedName("Signature")
    @Expose
    val signature: String?
)
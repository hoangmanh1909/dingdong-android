package com.ems.dingdong.model.response

import com.google.gson.annotations.SerializedName

data class VerifyLinkOtpResponse(
    @SerializedName("token")
    val token:String?
)
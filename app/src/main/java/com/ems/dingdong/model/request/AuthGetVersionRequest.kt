package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class AuthGetVersionRequest(
    @SerializedName("Platform")
    val platform:String?
)
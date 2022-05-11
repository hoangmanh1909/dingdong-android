package com.ems.dingdong.model.request

import com.google.gson.annotations.SerializedName

data class VietmapSearchRequest(
    @SerializedName("text")
    val text:String?,
    @SerializedName("longitude")
    val longitude:Double?,
    @SerializedName("latitude")
    val latitude:Double?
)